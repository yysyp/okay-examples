package ps.demo.mybatchupload.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SmsAutoConfiguration.class})
public @interface EnableSms {

}
