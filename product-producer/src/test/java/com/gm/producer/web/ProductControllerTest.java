package com.gm.producer.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.producer.components.product.usecase.ProductDto;
import com.gm.producer.components.product.usecase.PublishProductServiceImpl;
import com.gm.producer.usecase.ProductDtoMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PublishProductServiceImpl service;

    @Test
    public void shouldPublishPostedProduct() throws Exception {
        ProductDto dto = ProductDtoMother.createNiteJoggerShoes();

        mvc.perform(post("/api/products")
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).publish(dto);

    }

}
