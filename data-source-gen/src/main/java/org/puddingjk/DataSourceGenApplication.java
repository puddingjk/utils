package org.puddingjk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.wanwei.oneview.mapper")
@EnableAsync
@EnableTransactionManagement
@EnableFeignClients
public class DataSourceGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSourceGenApplication.class, args);
    }

}
