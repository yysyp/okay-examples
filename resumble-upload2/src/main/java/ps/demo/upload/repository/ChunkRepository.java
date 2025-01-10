package ps.demo.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.demo.upload.entity.ChunkRecord;
import ps.demo.upload.entity.FileRecord;

import java.util.List;
import java.util.Optional;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ChunkRepository extends JpaRepository<ChunkRecord, Long> {
    Optional<ChunkRecord> findByFileRecordIdAndChunkMd5(Long fileRecordId, String chunkMd5);
    Optional<ChunkRecord> findByFileRecordIdAndChunkMd5AndChunkIndexAndStatus(Long fileRecordId, String chunkMd5, long chunkIndex, String status);

    List<ChunkRecord> findByFileRecordId(Long fileRecordId);


}
