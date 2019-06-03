package com.gm.storage.domain;

import com.gm.storage.components.product.domain.Product;
import com.gm.storage.components.product.domain.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductRepositoryIt {

  @Autowired
  private ProductRepository repository;

  @Test
  public void shouldSave() {
    Product niteJoggerShoes = ProductMother.createNiteJoggerShoes();
    repository.save(niteJoggerShoes);

    assertEquals(repository.findById(niteJoggerShoes.getId()).get(), niteJoggerShoes);
  }

}
