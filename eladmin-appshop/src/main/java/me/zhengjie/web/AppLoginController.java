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
import me.zhengjie.utils.MyUtil;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.enums.ResponseCodeEnums;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
* @website https://eladmin.vip
* @author wenger
* @date 2024-03-13
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "app登录")
@RequestMapping("/web/app")
public class AppLoginController {

    /**
     * 前段项目是通过8013调用后台   后台接口直接调用8000访问
     * @return
     */
    @Log("登录app")
    @ApiOperation("登录app")
    @PostMapping(value = "/loginApp.do")
    @ResponseBody
    public Map<String, Object> loginApp(){
        return MyUtil.getResponseMessage(ResponseCodeEnums.SUCCESS);
    }

}
