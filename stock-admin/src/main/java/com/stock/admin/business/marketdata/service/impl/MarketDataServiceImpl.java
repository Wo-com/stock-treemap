package com.stock.admin.business.marketdata.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.stock.admin.business.marketdata.entity.MarketData;
import com.stock.admin.business.marketdata.entity.MarketDataRequest;
import com.stock.admin.business.marketdata.mapper.MarketDataMapper;
import com.stock.admin.business.marketdata.service.MarketDataService;
import com.stock.admin.common.entity.ApiResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 市场数据 Service 实现
 */
@Service
public class MarketDataServiceImpl extends ServiceImpl<MarketDataMapper, MarketData> implements MarketDataService {

    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    public ApiResult marketDataSaveOrUpdate(MarketData marketData) {
        return ApiResult.success(super.saveOrUpdate(marketData));
    }

    @Override
    public ApiResult remove(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(MarketData::getId, id);
        return ApiResult.success(super.remove(queryWrapper));
    }

    @Override
    public ApiResult info(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(MarketData::getId, id);
        return ApiResult.success(super.getOne(queryWrapper));
    }

    @Override
    public ApiResult marketDatalist() {
        return ApiResult.success(super.list());
    }

    @Override
    public ApiResult page(MarketDataRequest request) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page page = Page.of(request.getPageNo(), request.getPageSize());
        return ApiResult.success(super.page(page, queryWrapper));
    }

    @Override
    public ApiResult getByDate(String date) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(MarketData::getDataDate, date);
        return ApiResult.success(super.list(queryWrapper));
    }

    @Override
    public ApiResult getOne(String date) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(MarketData::getDataDate, date);
        return ApiResult.success(super.getOne(queryWrapper));
    }

    @Override
    public ApiResult spiderData(MarketDataRequest request) {
        try {
            // 处理日期范围，支持传入日期或使用默认值
            LocalDate startDate;
            LocalDate endDate;

            if (request.getStartDate() != null && !request.getStartDate().isEmpty()) {
                // 如果传入日期，使用该日期
                startDate = LocalDate.parse(request.getStartDate());
                endDate = LocalDate.parse(request.getEndDate());
            } else {
                // 默认爬取最近15天
                startDate = LocalDate.now().minusDays(15);
                endDate = LocalDate.now().minusDays(1);
            }

            // 需要抓取的时间点（HHmm）
            List<String> targetTimes = List.of("0930", "1000", "1030", "1100", "1130", "1330", "1400", "1430", "1500");

            List<String> results = new ArrayList<>();

            // 遍历日期范围
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                // 跳过周六(6)和周日(0)
                int dayOfWeek = currentDate.getDayOfWeek().getValue();
                if (dayOfWeek == 6 || dayOfWeek == 7) {
                    currentDate = currentDate.plusDays(1);
                    continue;
                }

                // 格式化日期为YYYY-MM-DD
                String dateStr = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                System.out.println("请求数据: " + dateStr);

                // 遍历时间点
                for (String time : targetTimes) {
                    boolean success = getHistoryDataByTime(dateStr, time);
                    if (!success) {
                        break;
                    }

                    results.add(dateStr + " " + time);

                    // 随机暂停5-10秒
                    Thread.sleep(5000 + (long) (Math.random() * 5000));
                }

                currentDate = currentDate.plusDays(1);
            }

            return ApiResult.success(results);
        } catch (Exception e) {
            return ApiResult.error("9999", "爬取数据失败: " + e.getMessage());
        }
    }

    /**
     * 获取并插入历史数据
     */
    private boolean getHistoryDataByTime(String yyMMdd, String hhmm) {
        try {
            String url = "https://quote.eastmoney.com/stockhotmap/api/getquotedata_history/" + yyMMdd + "true/" + hhmm;

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    return false;
                }

                String body = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(body);

                // 解析返回数据
                JsonNode resultNode = rootNode.path("result");
                JsonNode dataNode = resultNode.path("data");

                String resDate = dataNode.path("date").asText();
                String resTime = dataNode.path("time").asText();
                JsonNode dataArray = dataNode.path("data");
                if(dataArray.isEmpty()){
                    return false;
                }

                // 格式化日期
                String year = yyMMdd.substring(0, 4);
                String month = yyMMdd.substring(5, 7);
                String day = yyMMdd.substring(8, 10);
                String compactDate = year + month + day;

                // 验证日期匹配
                if (!compactDate.equals(resDate)) {
                    System.out.println("返回数据不匹配: " + compactDate + " vs " + resDate);
                    return false;
                }

                List<String> saveData = new ArrayList<>();

                // 处理数据数组
                for (int i = 0; i < dataArray.size(); i++) {
                    JsonNode itemNode = dataArray.get(i);
                    String item;

                    if (itemNode.isTextual()) {
                        item = itemNode.asText();
                        // 如果字符串不包含管道符，说明是加密的数据，需要解密
                        if (!item.contains("|")) {
                            String keyIv = (compactDate + i + resTime + compactDate).substring(0, 16);
                            item = decrypt(item, keyIv);
                        }
                    } else {
                        item = itemNode.toString();
                    }
                    saveData.add(item);
                }

                // 保存数据到数据库
                MarketData marketData = new MarketData();
                String hour = hhmm.substring(0, 2);
                String minute = hhmm.substring(2, 4);
                marketData.setDataDate(LocalDateTime.parse(yyMMdd + " " + hour + ":" + minute, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                marketData.setData(objectMapper.writeValueAsString(saveData));
                marketData.setCreateTime(LocalDateTime.now());
                marketData.setUpdateTime(LocalDateTime.now());

                super.save(marketData);
                return true;
            }
        } catch (Exception e) {
            System.err.println("获取数据失败: " + yyMMdd + " " + hhmm + " - " + e.getMessage());
            return false;
        }
    }

    /**
     * 获取当前数据
     */
    private MarketData getCurrentDataByTime() {
        try {
            String url = "https://quote.eastmoney.com/stockhotmap/api/getquotedata";

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    return null;
                }

                String body = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(body);

                // 解析返回数据
                JsonNode resultNode = rootNode.path("result");
                JsonNode dataNode = resultNode.path("data");

                String resDate = dataNode.path("date").asText();
                String resTime = dataNode.path("time").asText();
                JsonNode dataArray = dataNode.path("data");
                if(dataArray.isEmpty()){
                    return null;
                }

                // 格式化日期
                LocalDateTime now = LocalDateTime.now();
                int year = now.getYear();
                int month = now.getMonthValue();
                int day = now.getDayOfMonth();

                String compactDate = ""+year + month + day;



                List<String> dataList = new ArrayList<>();

                // 处理数据数组
                for (int i = 0; i < dataArray.size(); i++) {
                    JsonNode itemNode = dataArray.get(i);
                    String item;

                    if (itemNode.isTextual()) {
                        item = itemNode.asText();
                        // 如果字符串不包含管道符，说明是加密的数据，需要解密
                        if (!item.contains("|")) {
                            String keyIv = (compactDate + i + resTime + compactDate).substring(0, 16);
                            item = decrypt(item, keyIv);
                        }
                    } else {
                        item = itemNode.toString();
                    }
                    dataList.add(item);
                }

                // 保存数据到数据库
                MarketData marketData = new MarketData();
                marketData.setData(objectMapper.writeValueAsString(dataList));

                return marketData;
            }
        } catch (Exception e) {
            System.err.println("获取数据失败: "+ e.getMessage());
            return null;
        }
    }



    public static String decrypt(String encryptedData, String keyIv) throws Exception {
        byte[] cipherInput = Base64.getDecoder().decode(encryptedData);
        if (cipherInput.length < 16 || !Arrays.equals(Arrays.copyOfRange(cipherInput, 0, 8), "Salted__".getBytes(StandardCharsets.US_ASCII))) {
            throw new IllegalArgumentException("Encrypted payload is not in OpenSSL salted format");
        }

        byte[] salt = Arrays.copyOfRange(cipherInput, 8, 16);
        byte[] ciphertext = Arrays.copyOfRange(cipherInput, 16, cipherInput.length);
        byte[][] keyAndIvBytes = deriveKeyAndIv(keyIv + keyIv, salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyAndIvBytes[0], "AES"), new IvParameterSpec(keyAndIvBytes[1]));
        return new String(cipher.doFinal(ciphertext), StandardCharsets.UTF_8);
    }

    private static byte[][] deriveKeyAndIv(String password, byte[] salt) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] derived = new byte[48];
        byte[] block = new byte[0];
        int offset = 0;

        while (offset < derived.length) {
            md5.reset();
            md5.update(block);
            md5.update(password.getBytes(StandardCharsets.UTF_8));
            md5.update(salt);
            block = md5.digest();

            int copyLength = Math.min(block.length, derived.length - offset);
            System.arraycopy(block, 0, derived, offset, copyLength);
            offset += copyLength;
        }

        return new byte[][]{
                Arrays.copyOfRange(derived, 0, 32),
                Arrays.copyOfRange(derived, 32, 48)
        };
    }
}
