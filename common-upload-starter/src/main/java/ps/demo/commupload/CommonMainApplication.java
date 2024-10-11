package ps.demo.commupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ps.demo.commupload.config.CommonUploadAutoConfiguration;


@Slf4j
@SpringBootApplication(exclude = CommonUploadAutoConfiguration.class)
public class CommonMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMainApplication.class, args);
    }

}
