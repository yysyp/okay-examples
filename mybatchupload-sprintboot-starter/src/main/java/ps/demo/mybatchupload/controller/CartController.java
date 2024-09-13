package ps.demo.mybatchupload.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ps.demo.mybatchupload.dto.*;
import ps.demo.mybatchupload.entity.UploadDownloadExcel;

@Slf4j
@Tag(name = "CartController", description = "CartController")
@RestController
@RequestMapping("/cart")
public class CartController {


    @Operation(summary = "Cart to get basic info and its detail items")
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String cartDetail(@RequestParam(value = "userId") Long userId,
                                      @RequestParam(value = "cartId") Long cartId) {

        return "OK";
    }

    @Operation(summary = "Cart to additional add product to cart")
    @PostMapping("/add")
    public UploadDownloadExcel addToCart(@RequestBody @Validated UploadDownloadExcel req) {
        log.info("add to cart req={}", req);
        return req;
    }



}

