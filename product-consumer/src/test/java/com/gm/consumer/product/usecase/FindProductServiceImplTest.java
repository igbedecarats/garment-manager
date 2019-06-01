package com.gm.consumer.product.usecase;

import com.gm.consumer.product.domain.Product;
import com.gm.consumer.product.domain.ProductMother;
import com.gm.consumer.product.domain.ProductRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FindProductServiceImplTest {

    @InjectMocks
    private FindProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnAllProductsWhenFindingAllAndTheyExist() {
        Product niteJoggerShoes = ProductMother.createNiteJoggerShoes();
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(niteJoggerShoes));
        List<ProductDto> dtos = service.findAll();
        assertEquals(dtos.size(), 1);
        ProductDto dto = dtos.get(0);
        assertEquals(dto.getId(), niteJoggerShoes.getId());
        assertEquals(dto.getName(), niteJoggerShoes.getName());
        assertEquals(dto.getMeta_data().getSite_name(), niteJoggerShoes.getMetaData().getSiteName());
        assertEquals(dto.getPricing_information().getCurrentPrice(), niteJoggerShoes.getPricing().getCurrentPrice(), 0.0);
        assertEquals(dto.getProduct_description().getText(), niteJoggerShoes.getDescription().getText());
    }

    @Test
    public void shouldReturnNoProductsWhenFindingAllAndNoneExist() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(service.findAll().size(), 0);
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenFindingByIdAndItNotExists() {
        String id = "test";
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("No product was found with the id " + id);
        service.findById(id);
    }

    @Test
    public void shouldReturnProductWhenFindingByIdAndItExists() {
        Product niteJoggerShoes = ProductMother.createNiteJoggerShoes();
        String id = "BD7676";
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(niteJoggerShoes));
        ProductDto dto = service.findById(id);
        assertEquals(dto.getId(), niteJoggerShoes.getId());
        assertEquals(dto.getName(), niteJoggerShoes.getName());
        assertEquals(dto.getMeta_data().getSite_name(), niteJoggerShoes.getMetaData().getSiteName());
        assertEquals(dto.getPricing_information().getCurrentPrice(), niteJoggerShoes.getPricing().getCurrentPrice(), 0.0);
        assertEquals(dto.getProduct_description().getText(), niteJoggerShoes.getDescription().getText());
    }
}