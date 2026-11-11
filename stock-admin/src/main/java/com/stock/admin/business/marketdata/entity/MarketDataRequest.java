package com.stock.admin.business.marketdata.entity;

import lombok.Data;

/**
 * 市场数据请求参数
 */
@Data
public class MarketDataRequest {

    /**
     * 起始日期 (yyyy-MM-dd)
     */
    private String date;

    /**
     * 起始日期 (yyyy-MM-dd)
     */
    private String startDate;

    /**
     * 结束日期 (yyyy-MM-dd)
     */
    private String endDate;

    /**
     * 分页-当前页
     */
    private Integer pageNo = 1;

    /**
     * 分页-每页大小
     */
    private Integer pageSize = 10;

}
