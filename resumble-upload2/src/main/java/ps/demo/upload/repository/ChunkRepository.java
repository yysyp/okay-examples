package ps.demo.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.demo.upload.entity.ChunkRecord;
import ps.demo.upload.entity.FileRecord;

import java.util.List;
import java.util.Optional;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ChunkRepository extends JpaRepository<ChunkRecord, Long> {
    Optional<ChunkRecord> findByFileIdAndChunkMd5(Long fileId, String chunkMd5);
    Optional<ChunkRecord> findByFileIdAndChunkMd5AndStatus(Long fileId, String chunkMd5, String status);

    List<ChunkRecord> findByFileId(Long fileId);


}
