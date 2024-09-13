package ps.demo.usespringbootstarter;

import com.howtodoinjava.demo.batch.jobs.csvToDb.config.EnableBatchJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ps.demo.mybatchupload.config.EnableSms;
import ps.demo.mybatchupload.service.AliyunSmsSenderImpl;
import ps.demo.mybatchupload.service.TencentSmsSenderImpl;

//@EnableSms
@EnableBatchJob
@Slf4j
@SpringBootApplication
//@SpringBootApplication (scanBasePackages = {"ps.demo.**"})
public class UseStarterApplication implements ApplicationRunner {

    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        //long freeMemory = Runtime.getRuntime().freeMemory();
        log.info("--->>maxMemory={}m, totalMemory={}m, usedMemory={}m",
                maxMemory / 1024 / 1024, totalMemory / 1024 / 1024,
                (maxMemory - totalMemory) / 1024 / 1024);
        log.info("System.getenv() = {}", System.getenv());
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("Available processors = {}", processors);
        SpringApplication.run(UseStarterApplication.class, args);
    }

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

    public UseStarterApplication(JobLauncher jobLauncher, ApplicationContext applicationContext) {
        this.jobLauncher = jobLauncher;
        this.applicationContext = applicationContext;
    }

    @Autowired
    private AliyunSmsSenderImpl aliyunSmsSender;

    @Autowired
    private TencentSmsSenderImpl tencentSmsSender;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationArguments is: {}", args);

        aliyunSmsSender.send("Use aliyun ");

        tencentSmsSender.send("use tencent ");

        Job job = (Job) applicationContext.getBean("insertIntoDbFromCsvJob");

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        var jobExecution = jobLauncher.run(job, jobParameters);

        var batchStatus = jobExecution.getStatus();
        while (batchStatus.isRunning()) {
            System.out.println("Still running...");
            Thread.sleep(5000L);
        }

    }

}
