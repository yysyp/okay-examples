package ps.demo.upload.dto;

import lombok.*;

import java.util.List;

@Data
public class FileChunkRecordDto {

    private Long id;
    private String fileMd5;
    private long fileSize;
    private long lastModifyTime;
    private String fileType;

    private long totalChunks;
    private String fileName;
    private String description;

    private List<String> chunkMD5s;

}