package ps.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ps.demo.mybatchupload.config.EnableSms;
import ps.demo.mybatchupload.service.AliyunSmsSenderImpl;
import ps.demo.mybatchupload.service.TencentSmsSenderImpl;

@SpringBootApplication
@EnableSms
public class AutoconfigApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AutoconfigApplication.class, args);
        AliyunSmsSenderImpl aliyunSmsSender = applicationContext.getBean(AliyunSmsSenderImpl.class);
        aliyunSmsSender.send("Use aliyun ");
        TencentSmsSenderImpl tencentSmsSender = applicationContext.getBean(TencentSmsSenderImpl.class);
        tencentSmsSender.send("use tencent ");
    }
}