package ps.demo.commupload.batchjob.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import ps.demo.commupload.batchjob.model.Person;

public class PersonItemReadListener implements ItemReadListener<Person> {

  public static final Logger logger = LoggerFactory.getLogger(PersonItemReadListener.class);

  @Override
  public void beforeRead() {
    logger.info("Reading a new Person Record");
  }

  @Override
  public void afterRead(Person input) {
    logger.info("New Person record read : " + input);
  }

  @Override
  public void onReadError(Exception e) {
    logger.error("Error in reading the person record : " + e);
  }
}
