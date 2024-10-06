package ps.demo.commupload.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UploadMetaDto implements java.io.Serializable {

    @NotBlank(message = "File type can't be left empty")
    private String fileType;

    private String checksum;

    private Map<String, Object> extraParams = new HashMap<>();

}
