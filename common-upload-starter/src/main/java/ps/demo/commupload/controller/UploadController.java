package ps.demo.commupload.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ps.demo.commupload.dto.BookDto;
import ps.demo.commupload.error.BookNotFoundException;
import ps.demo.commupload.error.BookUnSupportedFieldPatchException;
import ps.demo.commupload.error.ClientErrorException;
import ps.demo.commupload.error.ServiceErrorException;
import ps.demo.commupload.service.BookService;


import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class UploadController {

    @Autowired
    private BookService bookService;



    @Operation(summary = "File upload with multipart form data")
    @PostMapping(value = "/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "key", required = false) String key,
                             HttpServletRequest req) {
        try {
            Thread.sleep(31*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (file == null) {
            throw new ClientErrorException();
        }
        try {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(key)) {
                key = fileName;
            }

            String uploadFolder = System.getProperty("user.dir") + "/upload-folder/";
            String destFileName = uploadFolder + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")) + fileName;

            File destFile = Paths.get(destFileName).toFile();
            log.info("--------->>File uploaded to file={}", destFile);
            destFile.getParentFile().mkdirs();

            //Use inputStream/outputStream to read write file.
            try (
                    BufferedInputStream bi = new BufferedInputStream(file.getInputStream());
                    OutputStream fout = new FileOutputStream(destFile);
                    BufferedOutputStream bo = new BufferedOutputStream(fout);
//                    InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
//                    BufferedReader br = new BufferedReader(inputStreamReader);
//                    FileWriter fileWriter = new FileWriter(destFile);
            ) {

                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = bi.read(buf)) != -1) {
                    bo.write(buf, 0, len);
                }

            }

            //Or use file transferTo method to transfer file.
            //file.transferTo(destFile);

        } catch (Exception e) {
            throw new ServiceErrorException(e);
        }
        return "SUCCESS";
    }


}
