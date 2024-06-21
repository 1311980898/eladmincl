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
package me.zhengjie.lose.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author wenger
* @date 2024-05-25
**/
@Entity
@Data
@Table(name="sw_lose")
public class SwLose implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`lose_name`")
    @ApiModelProperty(value = "物品名称")
    private String loseName;

    @Column(name = "`lose_type`")
    @ApiModelProperty(value = "物品类型")
    private String loseType;

    @Column(name = "`phone`")
    @ApiModelProperty(value = "联系方式")
    private String phone;

    @Column(name = "`remark`")
    @ApiModelProperty(value = "描述")
    private String remark;

    @Column(name = "`photo`")
    @ApiModelProperty(value = "物品图片")
    private String photo;

    public void copy(SwLose source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
