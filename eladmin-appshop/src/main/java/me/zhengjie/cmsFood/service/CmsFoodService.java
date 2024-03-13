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
package me.zhengjie.cmsFood.service;

import me.zhengjie.cmsFood.domain.CmsFood;
import me.zhengjie.cmsFood.service.dto.CmsFoodDto;
import me.zhengjie.cmsFood.service.dto.CmsFoodQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description 服务接口
* @author wenger
* @date 2024-03-13
**/
public interface CmsFoodService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<CmsFoodDto> queryAll(CmsFoodQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<CmsFoodDto>
    */
    List<CmsFoodDto> queryAll(CmsFoodQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param foodId ID
     * @return CmsFoodDto
     */
    CmsFoodDto findById(Integer foodId);

    /**
    * 创建
    * @param resources /
    */
    void create(CmsFood resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(CmsFood resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<CmsFoodDto> all, HttpServletResponse response) throws IOException;
}