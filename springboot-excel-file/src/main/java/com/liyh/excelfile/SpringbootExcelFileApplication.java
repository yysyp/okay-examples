package com.liyh.excelfile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liyh.excelfile.mapper")
public class SpringbootExcelFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExcelFileApplication.class, args);
    }

}
