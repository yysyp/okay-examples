package ps.demo.commupload.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ps.demo.commupload.dto.UploadMetaDto;

@Slf4j
@Service
public class BatchJobService {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

    public BatchJobService(JobLauncher jobLauncher, ApplicationContext applicationContext) {
        this.jobLauncher = jobLauncher;
        this.applicationContext = applicationContext;
    }

    public void startBatchJob(UploadMetaDto uploadMetaDto) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, InterruptedException {

        Job job = null;

        if (uploadMetaDto.getFileType().toUpperCase().endsWith(".CSV")) {
            job = (Job) applicationContext.getBean("insertIntoDbFromCsvJob");
        } else {
            job = (Job) applicationContext.getBean("insertIntoDbFromExcelJob");
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .addString("tempFile", (String) uploadMetaDto.getExtraParams().get("destFile"))
                .toJobParameters();

        var jobExecution = jobLauncher.run(job, jobParameters);

        var batchStatus = jobExecution.getStatus();
        while (batchStatus.isRunning()) {
            batchStatus = jobExecution.getStatus();
            log.info("Still running...");
            Thread.sleep(5000L);
        }
        log.info("Done!");
    }


}
