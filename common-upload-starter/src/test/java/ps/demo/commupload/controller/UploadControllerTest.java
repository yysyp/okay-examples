package ps.demo.commupload.controller;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import ps.demo.commonlibx.common.RestTemplateTool;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UploadControllerTest {

    //@Test
    public void testUploadFile() throws FileNotFoundException {

        String url = "http://localhost:10001/api/books/file?fileType={fileType}&checksum={checksum}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> formMap = createMultipleRequestBody("file", new FileUrlResource(ResourceUtils.getURL("classpath:csv/inputData.csv")));
        ParameterizedTypeReference<String> responseType = ParameterizedTypeReference.forType(String.class);
        Object[] uriVariables = new String [] {"string", "checksum1"};

        ResponseEntity<String> responseEntity = RestTemplateTool.getInstance().postSubmitFormMultiValueMapWithUriVariableObjectsForT(url, headers, formMap, responseType, uriVariables);
        String body = responseEntity.getBody();
        System.out.println("response body = " + body);

    }

    public MultiValueMap<String, Object> createMultipleRequestBody(String file, AbstractResource... resources) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Arrays.asList(resources).forEach((resource) -> {
            body.add(file, resource);
        });
        return body;
    }

}