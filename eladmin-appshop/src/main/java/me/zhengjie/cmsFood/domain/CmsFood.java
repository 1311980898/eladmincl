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
package me.zhengjie.cmsFood.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author wenger
* @date 2024-03-13
**/
@Entity
@Data
@Table(name="cms_food")
public class CmsFood implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`food_id`")
    @ApiModelProperty(value = "食品ID")
    private Integer foodId;

    @Column(name = "`food_name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "食品名称")
    private String foodName;

    @Column(name = "`description`")
    @ApiModelProperty(value = "描述")
    private String description;

    @Column(name = "`food_type`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "食品类型")
    private String foodType;

    @Column(name = "`price`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @Column(name = "`supplier`",nullable = false)
    @ApiModelProperty(value = "供应商")
    private String supplier;

    @Column(name = "`production_date`")
    @ApiModelProperty(value = "生产日期")
    private Timestamp productionDate;

    @Column(name = "`expiry_date`")
    @ApiModelProperty(value = "过期日期")
    private Timestamp expiryDate;

    @Column(name = "`storage_conditions`")
    @ApiModelProperty(value = "存储条件")
    private String storageConditions;

    @Column(name = "`unit`")
    @ApiModelProperty(value = "单位")
    private String unit;

    @Column(name = "`stock_quantity`")
    @ApiModelProperty(value = "库存数量")
    private Integer stockQuantity;

    @Column(name = "`photo_path`")
    @ApiModelProperty(value = "食品照片路径")
    private String photoPath;

    @Column(name = "`created_at`")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createdAt;

    @Column(name = "`updated_at`")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updatedAt;

    public void copy(CmsFood source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
