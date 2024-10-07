package ps.demo.starteruse.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ps.demo.commupload.dto.UploadMetaDto;
import ps.demo.commupload.listener.FileParseService;

import java.util.Random;

@Slf4j
@Service
public class MyUploadListenerService implements FileParseService {
    @Override
    public String parseFile(UploadMetaDto uploadMetaDto) {
        log.info("running listener, uploadMetaDto = {}", uploadMetaDto);


        return System.currentTimeMillis()+"";
    }
}
