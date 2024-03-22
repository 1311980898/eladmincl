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
package me.zhengjie.cmsOrder.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.cmsFood.service.CmsFoodService;
import me.zhengjie.cmsFood.service.dto.CmsFoodDto;
import me.zhengjie.cmsOrder.domain.CmsOrder;
import me.zhengjie.cmsOrder.service.CmsOrderService;
import me.zhengjie.cmsOrder.service.dto.CmsOrderQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;
import me.zhengjie.cmsOrder.service.dto.CmsOrderDto;

/**
* @website https://eladmin.vip
* @author wenger
* @date 2024-03-17
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "cmsOrder管理")
@RequestMapping("/api/cmsOrder")
public class CmsOrderController {

    private final CmsOrderService cmsOrderService;
    private final CmsFoodService cmsFoodService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsOrder:list')")
    public void exportCmsOrder(HttpServletResponse response, CmsOrderQueryCriteria criteria) throws IOException {
        cmsOrderService.download(cmsOrderService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询cmsOrder")
    @ApiOperation("查询cmsOrder")
    @PreAuthorize("@el.check('cmsOrder:list')")
    public ResponseEntity<PageResult<CmsOrderDto>> queryCmsOrder(CmsOrderQueryCriteria criteria, Pageable pageable){
        PageResult<CmsOrderDto> cmsOrderDtoPageResult = cmsOrderService.queryAll(criteria, pageable);
        for (CmsOrderDto cmsOrderDto : cmsOrderDtoPageResult.getContent()) {
            Integer productId = cmsOrderDto.getProductId();
            CmsFoodDto byId = cmsFoodService.findById(productId);
            if(null != byId){
                cmsOrderDto.setFood(byId);
                cmsOrderDto.setFoodName(byId.getFoodName());
                cmsOrderDto.setFoodUrl(byId.getPhotoPath());
            }
        }

        return new ResponseEntity<>(cmsOrderDtoPageResult,HttpStatus.OK);
    }

    @PostMapping
    @Log("新增cmsOrder")
    @ApiOperation("新增cmsOrder")
    @PreAuthorize("@el.check('cmsOrder:add')")
    public ResponseEntity<Object> createCmsOrder(@Validated @RequestBody CmsOrder resources){
        cmsOrderService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改cmsOrder")
    @ApiOperation("修改cmsOrder")
    @PreAuthorize("@el.check('cmsOrder:edit')")
    public ResponseEntity<Object> updateCmsOrder(@Validated @RequestBody CmsOrder resources){
        cmsOrderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除cmsOrder")
    @ApiOperation("删除cmsOrder")
    @PreAuthorize("@el.check('cmsOrder:del')")
    public ResponseEntity<Object> deleteCmsOrder(@RequestBody Integer[] ids) {
        cmsOrderService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
