package com.howtodoinjava.demo.batch.jobs.csvToDb.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ps.demo.mybatchupload.config.SmsProperties;
import ps.demo.mybatchupload.service.AliyunSmsSenderImpl;
import ps.demo.mybatchupload.service.TencentSmsSenderImpl;

@EnableConfigurationProperties(value = SmsProperties.class)
@Configuration
public class BatchJobAutoConfiguration {
    @Bean
    public AliyunSmsSenderImpl aliYunSmsSender(SmsProperties smsProperties) {
        System.out.println("-->Init AliyunSmsSenderImpl");
        return new AliyunSmsSenderImpl(smsProperties.getAliyun());
    }

    @Bean
    public TencentSmsSenderImpl tencentSmsSender(SmsProperties smsProperties) {
        System.out.println("-->Init TencentSmsSenderImpl");
        return new TencentSmsSenderImpl(smsProperties.getTencent());
    }



}
