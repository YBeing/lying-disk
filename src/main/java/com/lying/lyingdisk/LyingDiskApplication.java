package com.lying.lyingdisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.lying.lyingdisk.dao"})
public class LyingDiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyingDiskApplication.class, args);
    }

}
