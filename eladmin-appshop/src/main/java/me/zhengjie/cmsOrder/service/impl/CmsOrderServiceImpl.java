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
package me.zhengjie.cmsOrder.service.impl;

import me.zhengjie.cmsOrder.domain.CmsOrder;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.cmsOrder.repository.CmsOrderRepository;
import me.zhengjie.cmsOrder.service.CmsOrderService;
import me.zhengjie.cmsOrder.service.dto.CmsOrderDto;
import me.zhengjie.cmsOrder.service.dto.CmsOrderQueryCriteria;
import me.zhengjie.cmsOrder.service.mapstruct.CmsOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author wenger
* @date 2024-03-17
**/
@Service
@RequiredArgsConstructor
public class CmsOrderServiceImpl implements CmsOrderService {

    private final CmsOrderRepository cmsOrderRepository;
    private final CmsOrderMapper cmsOrderMapper;

    @Override
    public PageResult<CmsOrderDto> queryAll(CmsOrderQueryCriteria criteria, Pageable pageable){
        Page<CmsOrder> page = cmsOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(cmsOrderMapper::toDto));
    }

    @Override
    public List<CmsOrderDto> queryAll(CmsOrderQueryCriteria criteria){
        return cmsOrderMapper.toDto(cmsOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CmsOrderDto findById(Integer id) {
        CmsOrder cmsOrder = cmsOrderRepository.findById(id).orElseGet(CmsOrder::new);
        ValidationUtil.isNull(cmsOrder.getId(),"CmsOrder","id",id);
        return cmsOrderMapper.toDto(cmsOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CmsOrder resources) {
        cmsOrderRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsOrder resources) {
        CmsOrder cmsOrder = cmsOrderRepository.findById(resources.getId()).orElseGet(CmsOrder::new);
        ValidationUtil.isNull( cmsOrder.getId(),"CmsOrder","id",resources.getId());
        cmsOrder.copy(resources);
        cmsOrderRepository.save(cmsOrder);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            cmsOrderRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CmsOrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsOrderDto cmsOrder : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("订单号", cmsOrder.getOrderNo());
            map.put("商品id", cmsOrder.getProductId());
            map.put("下单时间", cmsOrder.getOrderDate());
            map.put("订单金额", cmsOrder.getTotalAmount());
            map.put("订单状态", cmsOrder.getStatus());
            map.put("创建时间", cmsOrder.getCreatedAt());
            map.put("修改时间", cmsOrder.getUpdatedAt());
            map.put("商品数量", cmsOrder.getOrderNum());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}