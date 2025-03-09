package ps.demo.jpademo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ps.demo.jpademo.dto.ChunkReqResultDto;
import ps.demo.jpademo.dto.FileChunkRecordDto;
import ps.demo.jpademo.dto.FileResultDto;
import ps.demo.jpademo.service.FileService;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/file-md5-check-create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FileResultDto fileMd5CheckCreate(@RequestBody FileChunkRecordDto fileChunkRecordDto)
            throws IOException {
        log.info("File check fileChunkRecordDto:{}", fileChunkRecordDto);
        FileResultDto resultDto = fileService.fileMd5SizeModTimeTypeComparingAndNotExistsCreate(fileChunkRecordDto);
        log.info("File check resultDto:{}", resultDto);
        return resultDto;
    }

    @PostMapping(value = "/chunk-md5-check-create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChunkReqResultDto chunkMd5CheckCreate(@RequestBody ChunkReqResultDto chunkReqResultDto)
            throws IOException {

        log.info("Chunk check chunkReqResultDto:{}", chunkReqResultDto);
        boolean exist = fileService.chunkMd5Check(chunkReqResultDto.getFileRecordId(), chunkReqResultDto.getChunkMd5(), chunkReqResultDto.getChunkIndex());
        chunkReqResultDto.setExist(exist);
        
        return chunkReqResultDto;
    }

    @PostMapping(value = "/upload-chunk", consumes = "multipart/form-data")
    public ChunkReqResultDto uploadChunk(@RequestParam("file") MultipartFile file,
                                              @RequestParam("fileRecordId") Long fileRecordId,
                                              @RequestParam("chunkMd5") String chunkMd5,
                                              @RequestParam("chunkIndex") Long chunkIndex
                                              ) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File chunk is empty");
        }
        fileService.uploadChunk(fileRecordId, chunkMd5, chunkIndex, file);

        return new ChunkReqResultDto(fileRecordId, chunkMd5, chunkIndex, true);
    }

    @GetMapping("/upload-success")
    public FileResultDto uploadSuccess(@RequestParam("fileRecordId") Long fileRecordId)
            throws NoSuchAlgorithmException, IOException {
        FileResultDto fileResultDto = new FileResultDto();
        fileResultDto.setFileRecordId(fileRecordId);
        fileResultDto.setExist(fileService.isFileUploaded(fileRecordId));
        return fileResultDto;
    }


//    @PostMapping(value = "/upload-chunk", consumes = "multipart/form-data")
//    public ResponseEntity<String> uploadChunk(@RequestParam("file") MultipartFile file,
//                                              @RequestParam("fileName") String fileName,
//                                              @RequestParam("chunkIndex") long chunkIndex,
//                                              @RequestParam("totalChunks") long totalChunks)
//            throws IOException {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("File is empty");
//        }
//        fileService.storeChunk(file, fileName, chunkIndex, totalChunks);
//        return ResponseEntity.ok("Chunk uploaded successfully");
//    }
//
//    @GetMapping("/checksum")
//    public ResponseEntity<String> getChecksum(@RequestParam("fileName") String fileName)
//            throws NoSuchAlgorithmException, IOException {
//        Path filePath = FileService.FILE_STORAGE_LOCATION.resolve(fileName);
//        String checksum = fileService.calculateChecksum(filePath);
//        return ResponseEntity.ok(checksum);
//    }

}
