package ps.demo.upload2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileUploadController {


    @PostMapping("/upload1")
    public ResponseEntity<String> secondUpload(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(required = false, value = "md5") String md5) {
        try {
//            // 检查数据库中是否已存在该文件
//            if (fileService.existsByMd5(md5)) {
//                return ResponseEntity.ok("文件已存在");
//            }
//            // 保存文件到服务器
//            file.transferTo(new File("/path/to/save/" + file.getOriginalFilename()));
//            // 保存文件信息到数据库
//            fileService.save(new FileInfo(file.getOriginalFilename(), DigestUtils.md5DigestAsHex(file.getInputStream())));
//            return ResponseEntity.ok("上传成功");
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败");
        }
    }


}
