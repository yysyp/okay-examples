package ps.demo.commupload.batchjob.job;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.extensions.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import ps.demo.commupload.batchjob.listener.JobCompletionNotificationListener;
import ps.demo.commupload.batchjob.listener.PersonItemReadListener;
import ps.demo.commupload.batchjob.model.Person;
import ps.demo.commupload.batchjob.processor.PersonItemProcessor;
import ps.demo.commupload.batchjob.reader.MyPoiItemReader;
import ps.demo.commupload.excel.PersonRowMapper;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
public class ExcelToDatabaseJob {

    private static final String INSERT_QUERY = """
      insert into person (first_name, last_name, age, active)
      values (:firstName,:lastName,:age,:active)""";

    private final JobRepository jobRepository;

    public ExcelToDatabaseJob(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    @Bean(name="insertIntoDbFromExcelJob")
    public Job insertIntoDbFromExcelJob(@Qualifier("excelStep1") Step excelStep1) {

        var name = "Persons Excel Import Job";
        var builder = new JobBuilder(name, jobRepository);

        return builder.start(excelStep1)
                //.next(step2)
                .listener(new JobCompletionNotificationListener(null))
                .build();
    }

    @Bean(name = "excelStep1")
    public Step excelStep1(@Qualifier("excelFileReader") ItemReader<Person> excelFileReader,
                      ItemWriter<Person> writer,
                      ItemProcessor<Person, Person> processor,
                      PlatformTransactionManager txManager) {
        var name = "INSERT Excel RECORDS To DB Step";
        var builder = new StepBuilder(name, jobRepository);
        return builder
                .<Person, Person>chunk(5, txManager)
                /*.faultTolerant()
                .retryLimit(3).retry(DeadlockLoserDataAccessException.class)*/
                .reader(excelFileReader)
                .listener(new PersonItemReadListener())
                //.processor(processor)
                //.listener(new PersonItemProcessor())
                .writer(writer)
                //.listener(new PersonItemWriteListener())
                .build();
    }

    @Bean(name = "excelFileReader")
    @StepScope
    public MyPoiItemReader<Person> excelFileReader(@Value("#{jobParameters['tempFile']}") String tempFilePath
                                                   ) {

        MyPoiItemReader<Person> reader = new MyPoiItemReader<>();
        reader.setResource(new FileSystemResource(tempFilePath)); // Path to your Excel file
        reader.setLinesToSkip(2); // Skip header row if present
//        BeanWrapperRowMapper<Person> beanWrapperRowMapper = new BeanWrapperRowMapper();
//        beanWrapperRowMapper.setTargetType(Person.class);
//        reader.setRowMapper(beanWrapperRowMapper); // Map Excel rows to User objects
        PersonRowMapper personRowMapper = new PersonRowMapper();
        personRowMapper.headerRowSets = reader.headerRowSets;
        personRowMapper.sheetNames = reader.sheetNames;
        reader.setRowMapper(personRowMapper);
        return reader;
    }

/* Comment out to avoid conflict with: ps.demo.commupload.batchjob.job.CsvToDatabaseJob.jdbcItemWriter */
//    @Bean
//    public JdbcBatchItemWriter<Person> jdbcItemWriter(DataSource dataSource) {
//        var provider = new BeanPropertyItemSqlParameterSourceProvider<Person>();
//        var itemWriter = new JdbcBatchItemWriter<Person>();
//        itemWriter.setDataSource(dataSource);
//        itemWriter.setSql(INSERT_QUERY);
//        itemWriter.setItemSqlParameterSourceProvider(provider);
//        return itemWriter;
//    }

//    @Bean
//    public PersonItemProcessor personItemProcessor() {
//        return new PersonItemProcessor();
//    }

}
