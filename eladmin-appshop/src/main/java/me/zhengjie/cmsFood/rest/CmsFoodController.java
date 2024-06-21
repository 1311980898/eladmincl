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
package me.zhengjie.cmsFood.rest;

import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.cmsFood.domain.CmsFood;
import me.zhengjie.cmsFood.service.CmsFoodService;
import me.zhengjie.cmsFood.service.dto.CmsFoodQueryCriteria;
import me.zhengjie.utils.StringUtils;
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
import me.zhengjie.cmsFood.service.dto.CmsFoodDto;

/**
* @website https://eladmin.vip
* @author wenger
* @date 2024-03-13
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "cmsFood管理")
@RequestMapping("/api/cmsFood")
public class CmsFoodController {

    private final CmsFoodService cmsFoodService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsFood:list')")
    public void exportCmsFood(HttpServletResponse response, CmsFoodQueryCriteria criteria) throws IOException {
        cmsFoodService.download(cmsFoodService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询cmsFood")
    @ApiOperation("查询cmsFood")
    @AnonymousAccess
    public ResponseEntity<PageResult<CmsFoodDto>> queryCmsFood(CmsFoodQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cmsFoodService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增cmsFood")
    @ApiOperation("新增cmsFood")
    @AnonymousAccess
    public ResponseEntity<Object> createCmsFood(@Validated @RequestBody CmsFood resources){
        if(StringUtils.isBlank(resources.getPhotoPath())){
            resources.setPhotoPath("https://img0.baidu.com/it/u=2181227728,1888178858&fm=253&fmt=auto&app=138&f=JPEG?w=749&h=500");
        }
        cmsFoodService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改cmsFood")
    @ApiOperation("修改cmsFood")
    @AnonymousAccess
    public ResponseEntity<Object> updateCmsFood(@Validated @RequestBody CmsFood resources){
        cmsFoodService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除cmsFood")
    @ApiOperation("删除cmsFood")
    @AnonymousAccess
    public ResponseEntity<Object> deleteCmsFood(@RequestBody Integer[] ids) {
        cmsFoodService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}