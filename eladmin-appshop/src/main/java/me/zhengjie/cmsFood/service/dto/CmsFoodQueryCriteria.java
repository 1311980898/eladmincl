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
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @website https://eladmin.vip
* @author wenger
* @date 2024-03-13
**/
@Data
public class CmsFoodQueryCriteria{

    /** 精确 */
    @Query
    private Integer foodId;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String foodName;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String description;

    /** 精确 */
    @Query
    private String foodType;

    /** 精确 */
    @Query
    private BigDecimal price;

    /** 精确 */
    @Query
    private String supplier;

    /** 精确 */
    @Query
    private String storageConditions;

    /** 精确 */
    @Query
    private String unit;

    /** 精确 */
    @Query
    private Integer stockQuantity;

    /** 精确 */
    @Query
    private String photoPath;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> productionDate;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> expiryDate;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createdAt;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updatedAt;
}