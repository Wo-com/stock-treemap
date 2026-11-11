package com.stock.admin.business.marketdata.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.stock.admin.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 市场数据表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(value = "market_data")
public class MarketData extends BaseEntity implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 数据日期
     */
    private LocalDateTime dataDate;

    /**
     * 数据内容
     */
    private String data;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    private LocalDateTime updateTime;

    /**
     * 说明
     */
    private String remark;

}
