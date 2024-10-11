package ps.demo.commupload.batchjob.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.extensions.excel.RowCallbackHandler;
import org.springframework.batch.extensions.excel.Sheet;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import org.springframework.core.io.Resource;
import ps.demo.commupload.batchjob.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class MyPoiItemReader<T> extends PoiItemReader<T> {

    public List<String[]> headerRowSets = new ArrayList<>();
    public List<String> sheetNames = new ArrayList<>();

    public MyPoiItemReader() {

        this.setSkippedRowsCallback(new RowCallbackHandler() {
            @Override
            public void handleRow(RowSet rowSet) {
                String[] rowData = rowSet.getCurrentRow();
                headerRowSets.add(Arrays.copyOf(rowData, rowData.length));
                int count = getNumberOfSheets();
                for (int i = 0; i < count; i++) {
                    sheetNames.add(getSheet(i).getName());
                }
                log.info("sheetNames={} rowSet at{} value{}", sheetNames, rowSet.getCurrentRowIndex(), rowSet);
            }
        });
    }


}
