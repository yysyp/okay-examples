package ps.demo.commupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ps.demo.commupload.entity.UploadMeta;


import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface UploadMetaRepository extends JpaRepository<UploadMeta, Long> {

    // Custom Query
    @Query("SELECT m FROM UploadMeta m WHERE m.uploadedDate > :date")
    List<UploadMeta> findByUploadedDateAfter(@Param("date") LocalDate date);

}
