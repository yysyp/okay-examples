package ps.demo.commupload.excel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import ps.demo.commupload.batchjob.model.Person;

@Slf4j
public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(RowSet rowSet) throws Exception {
        Person person = new Person();
        String[] columns = rowSet.getMetaData().getColumnNames();
        int i = 0;
        for (String column : columns) {
            log.info("column ["+(i++)+"] " + column);
        }
        person.setFirstName(rowSet.getProperties().getProperty("First Name"));
        person.setLastName(rowSet.getProperties().getProperty("Last Name"));
        person.setAge(Integer.valueOf(rowSet.getProperties().getProperty("Age")));
        person.setActive(Boolean.valueOf(rowSet.getProperties().getProperty("Active")));
        return person;
    }
}
