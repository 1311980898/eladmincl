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
package me.zhengjie.cmsOrder.service.dto;

import lombok.Data;
import me.zhengjie.cmsFood.domain.CmsFood;
import me.zhengjie.cmsFood.service.dto.CmsFoodDto;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author wenger
* @date 2024-03-17
**/
@Data
public class CmsOrderDto implements Serializable {

    private Integer id;

    /** 订单号 */
    private String orderNo;

    /** 商品id */
    private Integer productId;

    /** 下单时间 */
    private Timestamp orderDate;

    /** 订单金额 */
    private BigDecimal totalAmount;

    /** 订单状态 */
    private String status;

    /** 创建时间 */
    private Timestamp createdAt;

    /** 修改时间 */
    private Timestamp updatedAt;

    /** 商品数量 */
    private Integer orderNum;

    private CmsFoodDto food;
    private String foodName;
    private String foodUrl;
}
