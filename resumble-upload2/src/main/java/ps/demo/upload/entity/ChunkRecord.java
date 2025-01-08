package ps.demo.upload.entity;

import jakarta.persistence.*;
import lombok.*;
import ps.demo.upload.dto.FileChunkRecordDto;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="chunk_records")
public class ChunkRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fileId;

    private String chunkMd5;

    private long chunkIndex;

    private String status;

    @ManyToOne
    private FileRecord fileRecord;


}