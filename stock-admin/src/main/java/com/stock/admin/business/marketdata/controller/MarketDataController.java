package com.stock.admin.business.marketdata.controller;

import com.stock.admin.business.marketdata.entity.MarketData;
import com.stock.admin.business.marketdata.entity.MarketDataRequest;
import com.stock.admin.business.marketdata.service.MarketDataService;
import com.stock.admin.common.entity.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 市场数据 Controller
 */
@RestController
@RequestMapping("/marketdata")
public class MarketDataController {

    @Autowired
    private MarketDataService marketDataService;

    /**
     * 保存/更新
     */
    @PostMapping("/saveOrUpdate")
    public ApiResult saveOrUpdate(@RequestBody MarketData marketData) {
        return marketDataService.marketDataSaveOrUpdate(marketData);
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public ApiResult remove(@RequestBody MarketData marketData) {
        return marketDataService.remove(marketData.getId());
    }

    /**
     * 查询一个
     */
    @PostMapping("/info")
    public ApiResult info(@RequestBody MarketData marketData) {
        return marketDataService.info(marketData.getId());
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    public ApiResult page(@RequestBody MarketDataRequest request) {
        return marketDataService.page(request);
    }

    /**
     * 查询全部
     */
    @PostMapping("/list")
    public ApiResult list(@RequestBody MarketData marketData) {
        return marketDataService.marketDatalist();
    }

    /**
     * 根据日期查询
     */
    @PostMapping("/getByDate")
    public ApiResult getByDate(@RequestBody MarketDataRequest request) {
        return marketDataService.getByDate(request.getDate());
    }

    /**
     * 查询一个（根据日期时间）
     */
    @PostMapping("/getOne")
    public ApiResult getOne(@RequestBody MarketDataRequest request) {
        return marketDataService.getOne(request.getDate());
    }

    /**
     * 爬取数据
     */
    @PostMapping("/spiderData")
    public ApiResult spiderData(@RequestBody MarketDataRequest request) {
        return marketDataService.spiderData(request);
    }

}
