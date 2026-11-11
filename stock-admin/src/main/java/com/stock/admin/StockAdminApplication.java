package com.stock.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.stock.admin.*.*.mapper")
public class StockAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockAdminApplication.class, args);
    }

}
