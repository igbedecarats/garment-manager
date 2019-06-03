package com.gm.consumer.product.web;

import com.gm.consumer.product.domain.Product;
import com.gm.consumer.product.domain.ProductMother;
import com.gm.consumer.product.domain.ProductRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("mysql")
public class ProductControllerIt {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository repository;

    @Test
    public void shouldFindProductById() throws Exception {
        Product niteJoggerShoes = ProductMother.createNiteJoggerShoes();
        when(repository.findById(niteJoggerShoes.getId())).thenReturn(Optional.of(niteJoggerShoes));

        mvc.perform(get("/api/products/" + niteJoggerShoes.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name", is(niteJoggerShoes.getName())));
    }

    @Test
    public void shouldFindAllProducts() throws Exception {
        Product niteJoggerShoes = ProductMother.createNiteJoggerShoes();
        when(repository.findAll()).thenReturn(Collections.singletonList(niteJoggerShoes));

        mvc.perform(get("/api/products/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].name", is(niteJoggerShoes.getName())));
    }

}
