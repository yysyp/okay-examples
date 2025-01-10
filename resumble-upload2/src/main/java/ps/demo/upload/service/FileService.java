package ps.demo.upload.service;

import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ps.demo.upload.config.UploadConstants;
import ps.demo.upload.dto.FileChunkRecordDto;
import ps.demo.upload.dto.FileResultDto;
import ps.demo.upload.entity.ChunkRecord;
import ps.demo.upload.entity.FileRecord;
import ps.demo.upload.repository.ChunkRepository;
import ps.demo.upload.repository.FileRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class FileService {

    public static final Path FILE_STORAGE_LOCATION = Paths.get("upload-folder").toAbsolutePath().normalize();
    private final Set<Long> receivedChunks = new HashSet<>();

    private final FileRepository fileRepository;
    private final ChunkRepository chunkRepository;

    public FileService(FileRepository fileRepository, ChunkRepository chunkRepository) throws IOException {
        this.fileRepository = fileRepository;
        this.chunkRepository = chunkRepository;
        Files.createDirectories(FILE_STORAGE_LOCATION);
    }



    public boolean isFileUploaded(Long fileRecordId) {
        Optional<FileRecord> fileRecordOptional = fileRepository.findById(fileRecordId);
        if (!fileRecordOptional.isPresent()) {
            return false;
        }
        FileRecord fileRecord = fileRecordOptional.get();
        List<ChunkRecord> chunkRecordList = this.chunkRepository.findByFileRecordId(fileRecord.getId());
        if (fileRecord.getTotalChunks() != chunkRecordList.size()) {
            return false;
        }
        return !chunkRecordList.stream().anyMatch(ck -> UploadConstants.UPLOADING.equals(ck.getStatus()));

    }


    @Transactional
    public FileResultDto fileMd5SizeModTimeTypeComparingAndCopyCreate(FileChunkRecordDto fcrDto) {
        Optional<FileRecord> fileRecordOptional = fileRepository.findFirstByFileMd5AndFileSizeAndLastModifiedTimeAndFileType(
                fcrDto.getFileMd5(), fcrDto.getFileSize(), fcrDto.getLastModifiedTime(), fcrDto.getFileType());
        FileRecord fileRecord = FileRecord.fromDto(fcrDto);
        if (fileRecordOptional.isPresent()) {
            FileRecord fileRecordOld = fileRecordOptional.get();
            if (isFileUploaded(fileRecordOld.getId())) {
                fileRecord.setPath(fileRecordOld.getPath());
                this.fileRepository.save(fileRecord);
                return new FileResultDto(fileRecord.getId(), true);
            } else {
                fileRecord.setId(fileRecordOld.getId());
                fileRecord.setPath(fileRecordOld.getPath());
            }
        } else {
            fileRecord.setPath(UUID.randomUUID().toString());
        }
        FileRecord result = this.fileRepository.save(fileRecord);

        return new FileResultDto(result.getId(), false);
    }

    @Transactional
    public boolean chunkMd5CheckAndCopy(Long fileRecordId, String chunkMd5, Long chunkIndex) {
        Optional<ChunkRecord> chunkRecordOptional = chunkRepository.findByFileRecordIdAndChunkMd5AndChunkIndexAndStatus(fileRecordId, chunkMd5, chunkIndex, UploadConstants.UPLOADED);
        if (chunkRecordOptional.isPresent()) {
//            ChunkRecord chunkRecord = ChunkRecord.builder()
//                    .fileRecordId(fileRecordId)
//                    .chunkMd5(chunkMd5)
//                    .chunkIndex(chunkIndex)
//                    .build();
//            chunkRepository.save(chunkRecord);
            return true;
        }
        return false;
    }


    @Transactional
    public void uploadChunk(Long fileRecordId, String chunkMd5, Long chunkIndex, MultipartFile file) throws IOException {
        FileRecord fileRecord = fileRepository.findById(fileRecordId).get();
        Optional<ChunkRecord> chunkRecordOptional = chunkRepository.findByFileRecordIdAndChunkMd5(fileRecordId, chunkMd5);

        Path chunkPath = FILE_STORAGE_LOCATION.resolve(fileRecord.getPath() + "/" + UploadConstants.PART + chunkIndex);
        FileUtils.createParentDirectories(chunkPath.toFile());
        ChunkRecord chunkRecord = null;
        if (chunkRecordOptional.isPresent()) {
            chunkRecord = chunkRecordOptional.get();
            if (UploadConstants.UPLOADED.equals(chunkRecordOptional.get().getStatus())) {
                return;
            }
            Files.deleteIfExists(chunkPath);
        } else {
            chunkRecord = ChunkRecord.builder()
                    .fileRecord(fileRecord)
                    .chunkMd5(chunkMd5)
                    .chunkIndex(chunkIndex)
                    .status(UploadConstants.UPLOADING)
                    .build();
            chunkRepository.save(chunkRecord);
        }

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, chunkPath);
        }
        if (!chunkMd5.equals(genMd5(chunkPath.toFile()))) {
            Files.deleteIfExists(chunkPath);
            throw new RuntimeException("File fileRecordId:"+fileRecordId+" chunk index:"+chunkIndex+" mismatch!");
        }
        chunkRecord.setStatus(UploadConstants.UPLOADED);
        chunkRepository.save(chunkRecord);
    }


    private String genMd5(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fis);
        }
    }


/** -------------------- Old no use methods -------------------- **/
    public void storeChunk(MultipartFile file, String fileName, long chunkIndex, long totalChunks) throws IOException {
        Path chunkPath = FILE_STORAGE_LOCATION.resolve(fileName + UploadConstants.PART +chunkIndex);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, chunkPath);
        }
        synchronized (receivedChunks) {
            receivedChunks.add(chunkIndex);
            if (receivedChunks.size() == totalChunks) {
                assumbleFile(fileName, totalChunks);
                receivedChunks.clear();
            }
        }
    }

    private void assumbleFile(String fileName, long totalChunks) throws IOException {
        Path filePath = FILE_STORAGE_LOCATION.resolve(fileName);
        try (RandomAccessFile accessFile = new RandomAccessFile(filePath.toFile(), "rw")) {
            for (long i = 0; i < totalChunks; i++) {
                Path chunkPath = FILE_STORAGE_LOCATION.resolve(fileName + UploadConstants.PART + i);
                byte[] chunkData = Files.readAllBytes(chunkPath);
                accessFile.write(chunkData);
                Files.delete(chunkPath);
            }
        }
    }

    public String calculateChecksum(Path filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(filePath)) {
            byte[] bytes = new byte[8192];
            int reads;
            while ((reads = is.read(bytes)) != -1) {
                digest.update(bytes, 0, reads);
            }
        }
        byte[] hash = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}
