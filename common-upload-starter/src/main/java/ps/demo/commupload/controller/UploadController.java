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
import ps.demo.commupload.dto.UploadMeta;
import ps.demo.commupload.error.BookNotFoundException;
import ps.demo.commupload.error.BookUnSupportedFieldPatchException;
import ps.demo.commupload.error.ClientErrorException;
import ps.demo.commupload.error.ServiceErrorException;
import ps.demo.commupload.service.BookService;
import ps.demo.commupload.service.FileService;


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
    private FileService fileService;


    @Operation(summary = "File upload with multipart form data")
    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public String uploadFile(@RequestPart("file") MultipartFile file,
                             UploadMeta uploadMeta,
                             HttpServletRequest req) {
        try {
            File destFile = fileService.storeMultipartFile(file);

            return destFile.toString();

        } catch (Exception e) {
            throw new ServiceErrorException(e);
        }
    }




}
