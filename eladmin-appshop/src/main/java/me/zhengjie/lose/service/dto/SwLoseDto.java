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
package me.zhengjie.lose.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author wenger
* @date 2024-05-25
**/
@Data
public class SwLoseDto implements Serializable {

    private Integer id;

    /** 物品名称 */
    private String loseName;

    /** 物品类型 */
    private String loseType;

    /** 联系方式 */
    private String phone;

    /** 描述 */
    private String remark;

    /** 物品图片 */
    private String photo;
}