package ps.demo.commupload.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ps.demo.commupload.service.FileService;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/download")
public class DownloadController {

    @Autowired
    private FileService fileService;

    /**
     * path will be the relative path to upload-folder.
     * ex: 2022-04-07/example.jpg
     * @param path
     * @param request
     * @return
     */
    @Operation(summary = "File download by relative path from System.getProperty(\"user.dir\") + \"/upload-folder/\"")
    @GetMapping("/file/path")
    public ResponseEntity<Resource> downloadFileByPath(
            @RequestParam(value="path", required = true) String path
            , HttpServletRequest request) {
        return getResourceByFile(path, request);
    }
    

    private ResponseEntity<Resource> getResourceByFile(String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);

        //InputStream inputStream = inputStream = new FileInputStream(fileName);
        //Resource springResource = new InputStreamResource(inputStream);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("--------->>>Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }



}
