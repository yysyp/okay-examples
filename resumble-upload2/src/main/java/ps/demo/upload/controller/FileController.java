package ps.demo.upload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping(value = "/upload-chunk", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadChunk(@RequestParam("file") MultipartFile file,
                                              @RequestParam("fileName") String fileName,
                                              @RequestParam("chunkIndex") long chunkIndex,
                                              @RequestParam("totalChunks") long totalChunks)
            throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        fileService.storeChunk(file, fileName, chunkIndex, totalChunks);
        return ResponseEntity.ok("Chunk uploaded successfully");

    }

    @GetMapping("/checksum")
    public ResponseEntity<String> getChecksum(@RequestParam("fileName") String fileName)
            throws NoSuchAlgorithmException, IOException {

        Path filePath = FileService.FILE_STORAGE_LOCATION.resolve(fileName);
        String checksum = fileService.calculateChecksum(filePath);
        return ResponseEntity.ok(checksum);

    }

}
