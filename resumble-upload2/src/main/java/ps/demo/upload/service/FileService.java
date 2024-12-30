package ps.demo.upload.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

@Service
public class FileService {

    public static final Path FILE_STORAGE_LOCATION = Paths.get("upload-folder").toAbsolutePath().normalize();
    public static final String PART = ".part";
    private final Set<Long> receivedChunks = new HashSet<>();

    public FileService() throws IOException {
        Files.createDirectories(FILE_STORAGE_LOCATION);
    }

    public void storeChunk(MultipartFile file, String fileName, long chunkIndex, long totalChunks) throws IOException {
        Path chunkPath = FILE_STORAGE_LOCATION.resolve(fileName + PART +chunkIndex);
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
                Path chunkPath = FILE_STORAGE_LOCATION.resolve(fileName + PART + i);
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
