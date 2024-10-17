package ps.demo.commupload.batchjob.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.extensions.excel.RowCallbackHandler;
import org.springframework.batch.extensions.excel.Sheet;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import ps.demo.commupload.batchjob.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyPoiItemReader<T> extends PoiItemReader<T> implements StepExecutionListener {

    public List<String[]> headerRowSets = new ArrayList<>();
    public List<String> sheetNames = new ArrayList<>();

    public StepExecution stepExecution;


    public MyPoiItemReader() {

        this.setSkippedRowsCallback(new RowCallbackHandler() {
            @Override
            public void handleRow(RowSet rowSet) {
                String[] rowData = rowSet.getCurrentRow();
                headerRowSets.add(Arrays.copyOf(rowData, rowData.length));
//                int count = getNumberOfSheets();
//                for (int i = 0; i < count; i++) {
//                    sheetNames.add(getSheet(i).getName());
//                }
                log.info("rowSet at{} value{}", rowSet.getCurrentRowIndex(), rowSet);
            }
        });
    }




    @Override
    protected void openExcelFile(Resource resource, String password) throws Exception {
        super.openExcelFile(resource, password);
        int count = getNumberOfSheets();
        for (int i = 0; i < count; i++) {
            sheetNames.add(getSheet(i).getName());
        }
        log.info("sheetNames={}", sheetNames);


    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepExecutionListener.super.beforeStep(stepExecution);
        this.stepExecution = stepExecution;
        Map<String, Object> contextMap = stepExecution.getJobExecution().getExecutionContext().toMap();
        log.info("Job execution context map={}", contextMap);
        stepExecution.getJobExecution().getExecutionContext().put("addFromItemReader", System.currentTimeMillis());

    }
}
