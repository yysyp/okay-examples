package ps.demo.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ps.demo.upload.entity.FileRecord;
import ps.demo.upload.entity.Role;

import java.util.List;
import java.util.Optional;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface FileRepository extends JpaRepository<FileRecord, Long> {

    Optional<FileRecord> findByFileMd5AndFileSizeAndLastModifyTimeAndFileType(String fileMd5, long fileSize, long lastModifyTime, String fileType);


}
