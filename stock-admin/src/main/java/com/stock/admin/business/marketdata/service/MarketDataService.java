package com.stock.admin.business.marketdata.service;

import com.mybatisflex.core.service.IService;
import com.stock.admin.business.marketdata.entity.MarketData;
import com.stock.admin.business.marketdata.entity.MarketDataRequest;
import com.stock.admin.common.entity.ApiResult;

/**
 * 市场数据 Service
 */
public interface MarketDataService extends IService<MarketData> {

    ApiResult marketDataSaveOrUpdate(MarketData marketData);

    ApiResult remove(Long id);

    ApiResult info(Long id);

    ApiResult marketDatalist();

    ApiResult page(MarketDataRequest request);

    ApiResult getByDate(String date);

    ApiResult getOne(String date);

    ApiResult spiderData(MarketDataRequest request);

}
