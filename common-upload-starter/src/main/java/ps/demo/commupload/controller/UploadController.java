package ps.demo.commupload.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ps.demo.commupload.dto.UploadMetaDto;
import ps.demo.commupload.error.ServiceErrorException;
import ps.demo.commupload.listener.FileParseService;
import ps.demo.commupload.service.BatchJobService;
import ps.demo.commupload.service.FileService;
import ps.demo.commupload.service.UploadMetaService;


import java.io.*;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class UploadController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UploadMetaService uploadMetaService;

    @Autowired
    private BatchJobService batchJobService;

    @Autowired
    private ApplicationContext applicationContext;



    @Operation(summary = "File upload with multipart form data")
    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public UploadMetaDto uploadFile(@RequestPart("file") MultipartFile file,
                                    @Valid UploadMetaDto uploadMetaDto,
                             HttpServletRequest req) {
        try {
            File destFile = fileService.storeMultipartFile(file);
            uploadMetaDto.getExtraParams().put("destFile", destFile.getCanonicalFile().getPath());
            UploadMetaDto result = uploadMetaService.save(uploadMetaDto);
            batchJobService.startBatchJob(result);

            if (applicationContext.getBeanNamesForType(FileParseService.class).length > 0) {
                log.info("calling listener");
                FileParseService fileParseService = applicationContext.getBean(FileParseService.class);
                fileParseService.parseFile(result);
            }

            return result;

        } catch (Exception e) {
            throw new ServiceErrorException(e);
        }
    }


}
