/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.cmsFood.domain.CmsFood;
import me.zhengjie.cmsFood.service.CmsFoodService;
import me.zhengjie.cmsFood.service.dto.CmsFoodDto;
import me.zhengjie.cmsFood.service.dto.CmsFoodQueryCriteria;
import me.zhengjie.cmsOrder.domain.CmsOrder;
import me.zhengjie.cmsOrder.service.CmsOrderService;
import me.zhengjie.cmsOrder.service.dto.CmsOrderDto;
import me.zhengjie.cmsOrder.service.dto.CmsOrderQueryCriteria;
import me.zhengjie.utils.MyUtil;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.enums.ResponseCodeEnums;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author wenger
 * @website https://eladmin.vip
 * @date 2024-03-13
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "app接口")
@RequestMapping("/web/app")
public class AppLoginController {

    private final CmsFoodService cmsFoodService;

    private final CmsOrderService cmsOrderService;

    /**
     * 前段项目是通过8013调用后台   后台接口直接调用8000访问
     *
     * @return
     */
    @Log("登录app")
    @ApiOperation("登录app")
    @PostMapping(value = "/loginApp.do")
    @ResponseBody
    public Map<String, Object> loginApp(String username, String password) {
        if (StringUtils.isBlank(username)) {
            return MyUtil.getResponseMessage(ResponseCodeEnums.REQUIRED_FAILED, "用户名不能为空!");
        }
        if (StringUtils.isBlank(password)) {
            return MyUtil.getResponseMessage(ResponseCodeEnums.REQUIRED_FAILED, "密码不能为空!");
        }
        if (!"shop".equals(username)) {
            return MyUtil.getResponseMessage(ResponseCodeEnums.REQUIRED_FAILED, "用户名不存在!");
        }
        if (!"123456".equals(password)) {
            return MyUtil.getResponseMessage(ResponseCodeEnums.REQUIRED_FAILED, "密码不正确!");
        }
        return MyUtil.getResponseMessage(ResponseCodeEnums.SUCCESS);
    }


    /**
     * 查询食品列表的接口
     *
     * @return
     */
    @Log("查询食品列表")
    @ApiOperation("查询食品列表")
    @PostMapping(value = "/getFoodList.do")
    @ResponseBody
    public Map<String, Object> getFoodList(CmsFoodQueryCriteria criteria) {
        List<CmsFoodDto> cmsFoodDtos = cmsFoodService.queryAll(criteria);
        return MyUtil.getResponseMessage(ResponseCodeEnums.SUCCESS, cmsFoodDtos);
    }


    /**
     * 查询食品列表的接口
     *
     * @return
     */
    @Log("查询订单列表")
    @ApiOperation("查询订单列表")
    @PostMapping(value = "/getOrderList.do")
    @ResponseBody
    public Map<String, Object> getOrderList(CmsOrderQueryCriteria criteria) {
        List<CmsOrderDto> cmsOrderDtos = cmsOrderService.queryAll(criteria);
        for (CmsOrderDto cmsOrderDto : cmsOrderDtos) {
            Integer productId = cmsOrderDto.getProductId();
            CmsFoodDto byId = cmsFoodService.findById(productId);
            if(null != byId){
                cmsOrderDto.setFood(byId);
            }
        }
        return MyUtil.getResponseMessage(ResponseCodeEnums.SUCCESS, cmsOrderDtos);
    }


    /**
     * 下单接口
     *
     * @return
     */
    @Log("下订单")
    @ApiOperation("下订单")
    @PostMapping(value = "/upOrder.do")
    @ResponseBody
    public Map<String, Object> upOrder(CmsOrder cmsOrder) {
        if (null == cmsOrder.getProductId()) {
            return MyUtil.getResponseMessage(ResponseCodeEnums.REQUIRED_FAILED, "商品不能为空!");
        }
        if (null == cmsOrder.getTotalAmount()) {
            return MyUtil.getResponseMessage(ResponseCodeEnums.REQUIRED_FAILED, "金额不能为空!");
        }

        cmsOrder.setOrderNo(MyUtil.generateUniqueKey());
        cmsOrder.setOrderDate(MyUtil.getTimestamp());
        cmsOrder.setCreatedAt(MyUtil.getTimestamp());
        cmsOrder.setStatus("已支付");
        cmsOrderService.create(cmsOrder);
        return MyUtil.getResponseMessage(ResponseCodeEnums.SUCCESS);
    }
}
