package ps.demo.mybatchupload.entity;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "task_cache")
//@Cacheable("task_cache")
public class TaskCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date paramDate;
    private String paramSite;
    private String resultData;

}
