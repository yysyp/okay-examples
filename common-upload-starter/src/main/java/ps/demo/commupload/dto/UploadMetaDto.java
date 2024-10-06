package ps.demo.commupload.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UploadMetaDto implements java.io.Serializable {

    private String fileType;

    private String checksum;

    private Map<String, Object> extraParams = new HashMap<>();

}
