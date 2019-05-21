package com.gm.producer.components.product.web;

import com.gm.producer.components.product.usecase.ProductDto;
import com.gm.producer.components.product.usecase.PublishProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(
        value = "products",
        tags = {"products"}
)
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private PublishProductService publishProductService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Publish a product to be stored",
            produces = "application/json"
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public void postProduct(
            @ApiParam(value = "The product to be published", required = true) @RequestBody final ProductDto dto) {
        publishProductService.publish(dto);
    }
}
