package com.gm.consumer.product.usecase;

import java.util.List;

public interface FindProductService {

    ProductDto findById(final String id);

    List<ProductDto> findAll();
}
