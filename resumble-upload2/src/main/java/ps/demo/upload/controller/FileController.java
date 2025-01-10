package ps.demo.upload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ps.demo.upload.dto.ChunkReqResultDto;
import ps.demo.upload.dto.FileChunkRecordDto;
import ps.demo.upload.dto.FileResultDto;
import ps.demo.upload.service.FileService;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

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

        return fileService.fileMd5SizeModTimeTypeComparingAndCopyCreate(fileChunkRecordDto);
    }

    @PostMapping(value = "/chunk-md5-check-create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChunkReqResultDto chunkMd5CheckCreate(@RequestBody ChunkReqResultDto chunkReqResultDto)
            throws IOException {

        boolean exist = fileService.chunkMd5CheckAndCopy(chunkReqResultDto.getFileRecordId(), chunkReqResultDto.getChunkMd5(), chunkReqResultDto.getChunkIndex());

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
