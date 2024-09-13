package com.howtodoinjava.demo.batch.jobs.csvToDb.config;

import com.howtodoinjava.demo.batch.jobs.csvToDb.job.CsvToDatabaseJob;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Import({BatchJobAutoConfiguration.class})
@Import({BatchJobAutoConfiguration.class, BatchConfig.class, CsvToDatabaseJob.class,  })
public @interface EnableBatchJob {

}
