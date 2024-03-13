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
package me.zhengjie.cmsFood.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author wenger
* @date 2024-03-13
**/
@Data
public class CmsFoodDto implements Serializable {

    /** 食品ID */
    private Integer foodId;

    /** 食品名称 */
    private String foodName;

    /** 描述 */
    private String description;

    /** 食品类型 */
    private String foodType;

    /** 价格 */
    private BigDecimal price;

    /** 供应商 */
    private String supplier;

    /** 生产日期 */
    private Timestamp productionDate;

    /** 过期日期 */
    private Timestamp expiryDate;

    /** 存储条件 */
    private String storageConditions;

    /** 单位 */
    private String unit;

    /** 库存数量 */
    private Integer stockQuantity;

    /** 食品照片路径 */
    private String photoPath;

    /** 创建时间 */
    private Timestamp createdAt;

    /** 更新时间 */
    private Timestamp updatedAt;
}