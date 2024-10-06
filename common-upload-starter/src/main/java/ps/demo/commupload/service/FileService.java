package ps.demo.commupload.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by yunpeng.song on 5/15/2020.
 */

@Slf4j
@Service
public class FileService {
    private final Path fileStorageLocation;

    @Autowired
    public FileService() {
        String uploadFolder = System.getProperty("user.dir") + "/upload-folder/";
        this.fileStorageLocation = Paths.get(uploadFolder).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the upload directory.", ex);
        }
    }

    public File storeMultipartFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        String uploadFolder = System.getProperty("user.dir") + "/upload-folder/";
        String destFileName = uploadFolder + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")) + fileName;
        File destFile = Paths.get(destFileName).toFile();
        log.info("File uploaded to file={}", destFile);
        destFile.getParentFile().mkdirs();

        // Use inputStream/outputStream to read write file.
        try (
                BufferedInputStream bi = new BufferedInputStream(file.getInputStream());
                OutputStream fout = new FileOutputStream(destFile);
                BufferedOutputStream bo = new BufferedOutputStream(fout);
        ) {

            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = bi.read(buf)) != -1) {
                bo.write(buf, 0, len);
            }

        }

        // Or use file transferTo method to transfer file.
        //file.transferTo(destFile);

        return destFile;
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

}
