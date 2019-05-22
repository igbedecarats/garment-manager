package com.gm.consumer.product.web;

import com.gm.consumer.product.usecase.FindProductService;
import com.gm.consumer.product.usecase.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
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
    private FindProductService findProductService;

    @GetMapping(value = "/{id:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Gets a product by its id.",
            produces = "application/json"
    )
    @ResponseStatus(value = HttpStatus.OK)
    public ProductDto gettProductById(
            @ApiParam(value = "The id of the product to be fetched", required = true) @PathVariable final String id) {
        return findProductService.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Gets all the stored products.",
            produces = "application/json"
    )
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductDto> postProduct() {
        return findProductService.findAll();
    }
}
