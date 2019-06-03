package com.gm.producer.usecase;

import com.gm.producer.components.product.usecase.ProductDto;

public class ProductDtoMother {

    public static ProductDto createNiteJoggerShoes() {
        ProductDto dto = new ProductDto();
        dto.setId("BD7676");
        dto.setModel_number("BTO93");
        dto.setName("Nite Jogger Shoes");
        dto.setProduct_type("inline");
        dto.setMeta_data(new ProductDto.MetadataDto(
                "adidas Nite Jogger Shoes - White | adidas UK",
                "adidas United Kingdom",
                "Shop for Nite Jogger Shoes - White at adidas.co.uk! See all the styles and colours of Nite Jogger Shoes - White at the official adidas UK online store.",
                "Nite Jogger Shoes",
                "//www.adidas.co.uk/nite-jogger-shoes/BD7676.html"
        ));
        dto.setPricing_information(new ProductDto.PricingDto(
                99.95F, 99.95F, 83.29F
        ));
        dto.setProduct_description(new ProductDto.DescriptionDto(
                "Nite Jogger Shoes",
                "Modern cushioning updates this flashy 80s standout.",
                "Inspired by the 1980 Nite Jogger, these shoes shine bright with retro style and reflective details. The mesh and nylon ripstop upper is detailed with suede overlays. An updated Boost midsole adds responsive cushioning for all-day comfort."
        ));
        return dto;
    }
}
