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
package me.zhengjie.lose.service.impl;

import me.zhengjie.lose.domain.SwLose;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.lose.repository.SwLoseRepository;
import me.zhengjie.lose.service.SwLoseService;
import me.zhengjie.lose.service.dto.SwLoseDto;
import me.zhengjie.lose.service.dto.SwLoseQueryCriteria;
import me.zhengjie.lose.service.mapstruct.SwLoseMapper;
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
* @date 2024-05-25
**/
@Service
@RequiredArgsConstructor
public class SwLoseServiceImpl implements SwLoseService {

    private final SwLoseRepository swLoseRepository;
    private final SwLoseMapper swLoseMapper;

    @Override
    public PageResult<SwLoseDto> queryAll(SwLoseQueryCriteria criteria, Pageable pageable){
        Page<SwLose> page = swLoseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(swLoseMapper::toDto));
    }

    @Override
    public List<SwLoseDto> queryAll(SwLoseQueryCriteria criteria){
        return swLoseMapper.toDto(swLoseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SwLoseDto findById(Integer id) {
        SwLose swLose = swLoseRepository.findById(id).orElseGet(SwLose::new);
        ValidationUtil.isNull(swLose.getId(),"SwLose","id",id);
        return swLoseMapper.toDto(swLose);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SwLose resources) {
        swLoseRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SwLose resources) {
        SwLose swLose = swLoseRepository.findById(resources.getId()).orElseGet(SwLose::new);
        ValidationUtil.isNull( swLose.getId(),"SwLose","id",resources.getId());
        swLose.copy(resources);
        swLoseRepository.save(swLose);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            swLoseRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SwLoseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SwLoseDto swLose : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("物品名称", swLose.getLoseName());
            map.put("物品类型", swLose.getLoseType());
            map.put("联系方式", swLose.getPhone());
            map.put("描述", swLose.getRemark());
            map.put("物品图片", swLose.getPhoto());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}