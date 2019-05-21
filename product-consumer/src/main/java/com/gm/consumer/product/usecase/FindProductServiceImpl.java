package com.gm.consumer.product.usecase;

import com.gm.consumer.product.domain.ProductRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindProductServiceImpl implements FindProductService {

  private ProductRepository productRepository;

  @Autowired
  public FindProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductDto findById(final String id) {
    return ProductDto.fromEntity(
        productRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("No product was found with the id " + id)));
  }
}
