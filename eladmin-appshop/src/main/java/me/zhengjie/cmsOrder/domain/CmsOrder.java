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
package me.zhengjie.cmsOrder.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author wenger
* @date 2024-03-17
**/
@Entity
@Data
@Table(name="cms_order")
public class CmsOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`order_no`")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @Column(name = "`product_id`")
    @ApiModelProperty(value = "商品id")
    private Integer productId;

    @Column(name = "`order_date`")
    @ApiModelProperty(value = "下单时间")
    private Timestamp orderDate;

    @Column(name = "`total_amount`")
    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalAmount;

    @Column(name = "`status`")
    @ApiModelProperty(value = "订单状态")
    private String status;

    @Column(name = "`created_at`")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createdAt;

    @Column(name = "`updated_at`")
    @ApiModelProperty(value = "修改时间")
    private Timestamp updatedAt;

    @Column(name = "`order_num`")
    @ApiModelProperty(value = "商品数量")
    private Integer orderNum;

    public void copy(CmsOrder source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
