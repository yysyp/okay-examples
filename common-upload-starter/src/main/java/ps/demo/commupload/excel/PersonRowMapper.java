package ps.demo.commupload.excel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import ps.demo.commupload.batchjob.model.Person;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PersonRowMapper implements RowMapper<Person> {
    public List<String[]> headerRowSets;
    public List<String> sheetNames;

    @Override
    public Person mapRow(RowSet rowSet) throws Exception {
        log.info("Sheet sheetNames: " + sheetNames);
        Person person = new Person();
        try {
//            String[] columns = rowSet.getMetaData().getColumnNames();
//            int i = 0;
//            for (String column : columns) {
//                log.info("column [" + (i++) + "] " + column);
//            }
            String[] rowData = rowSet.getCurrentRow();
            //person.setFirstName(rowSet.getProperties().getProperty("First Name"));
            person.setFirstName(rowData[1]);
            //person.setLastName(rowSet.getProperties().getProperty("Last Name"));
            person.setLastName(rowData[2]);
            //person.setAge(Integer.valueOf(rowSet.getProperties().getProperty("Age")));
            person.setAge(Integer.valueOf(rowData[3]));
            //person.setActive(Boolean.valueOf(rowSet.getProperties().getProperty("Active")));
            person.setActive(Boolean.valueOf(rowData[4]));
            return person;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
