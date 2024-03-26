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
package me.zhengjie.cmsFood.service.impl;

import me.zhengjie.cmsFood.domain.CmsFood;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.cmsFood.repository.CmsFoodRepository;
import me.zhengjie.cmsFood.service.CmsFoodService;
import me.zhengjie.cmsFood.service.dto.CmsFoodDto;
import me.zhengjie.cmsFood.service.dto.CmsFoodQueryCriteria;
import me.zhengjie.cmsFood.service.mapstruct.CmsFoodMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author wenger
* @date 2024-03-13
**/
@Service
@RequiredArgsConstructor
public class CmsFoodServiceImpl implements CmsFoodService {

    private final CmsFoodRepository cmsFoodRepository;
    private final CmsFoodMapper cmsFoodMapper;

    @Override
    public PageResult<CmsFoodDto> queryAll(CmsFoodQueryCriteria criteria, Pageable pageable){
        Page<CmsFood> page = cmsFoodRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(cmsFoodMapper::toDto));
    }

    @Override
    public List<CmsFoodDto> queryAll(CmsFoodQueryCriteria criteria){
        return cmsFoodMapper.toDto(cmsFoodRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CmsFoodDto findById(Integer foodId) {
        CmsFood cmsFood = cmsFoodRepository.findById(foodId).orElseGet(CmsFood::new);
        ValidationUtil.isNull(cmsFood.getFoodId(),"CmsFood","foodId",foodId);
        return cmsFoodMapper.toDto(cmsFood);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CmsFood resources) {
        if(StringUtils.isBlank(resources.getSupplier())){
            resources.setSupplier("1");
        }
        cmsFoodRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsFood resources) {
        CmsFood cmsFood = cmsFoodRepository.findById(resources.getFoodId()).orElseGet(CmsFood::new);
        ValidationUtil.isNull( cmsFood.getFoodId(),"CmsFood","id",resources.getFoodId());
        cmsFood.copy(resources);
        cmsFoodRepository.save(cmsFood);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer foodId : ids) {
            cmsFoodRepository.deleteById(foodId);
        }
    }

    @Override
    public void download(List<CmsFoodDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsFoodDto cmsFood : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("食品名称", cmsFood.getFoodName());
            map.put("描述", cmsFood.getDescription());
            map.put("食品类型", cmsFood.getFoodType());
            map.put("价格", cmsFood.getPrice());
            map.put("供应商", cmsFood.getSupplier());
            map.put("生产日期", cmsFood.getProductionDate());
            map.put("过期日期", cmsFood.getExpiryDate());
            map.put("存储条件", cmsFood.getStorageConditions());
            map.put("单位", cmsFood.getUnit());
            map.put("库存数量", cmsFood.getStockQuantity());
            map.put("食品照片路径", cmsFood.getPhotoPath());
            map.put("创建时间", cmsFood.getCreatedAt());
            map.put("更新时间", cmsFood.getUpdatedAt());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
