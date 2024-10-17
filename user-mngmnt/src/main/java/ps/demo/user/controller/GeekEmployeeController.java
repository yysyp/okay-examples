package ps.demo.user.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ps.demo.user.dto.GeekEmployee;

@Slf4j
@RestController
public class GeekEmployeeController {

    @PostMapping("/geekemployees")
    public ResponseEntity<GeekEmployee> saveEmployeeData(
            @Valid @RequestBody GeekEmployee geekEmployee) {
        log.info("Geek employee geekEmployee:{}", geekEmployee);
        return new ResponseEntity<GeekEmployee>(
                geekEmployee, HttpStatus.CREATED);
    }

}
