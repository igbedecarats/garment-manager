package com.gm.storage.domain;

import com.gm.storage.components.product.domain.Description;
import com.gm.storage.components.product.domain.Metadata;
import com.gm.storage.components.product.domain.Pricing;
import com.gm.storage.components.product.domain.Product;
import java.util.Arrays;

public class ProductMother {

    public static Product createNiteJoggerShoes() {
        Product product = new Product();
        product.setId("BD7676");
        product.setModelNumber("BTO93");
        product.setName("Nite Jogger Shoes");
        product.setProductType("inline");
        product.setMetaData(new Metadata(
                "BD7676",
                product,
                "adidas Nite Jogger Shoes - White | adidas UK",
                "adidas United Kingdom",
                "Shop for Nite Jogger Shoes - White at adidas.co.uk! See all the styles and colours of Nite Jogger Shoes - White at the official adidas UK online store.",
                Arrays.asList("Nite", "Jogger", "Shoes"),
                "//www.adidas.co.uk/nite-jogger-shoes/BD7676.html"
        ));
        product.setPricing(new Pricing(
                "BD7676", product, 99.95F, 99.95F, 83.29F
        ));
        product.setDescription(new Description(
                "BD7676", product, "Nite Jogger Shoes", "Modern cushioning updates this flashy 80s standout.", "Inspired by the 1980 Nite Jogger, these shoes shine bright with retro style and reflective details. The mesh and nylon ripstop upper is detailed with suede overlays. An updated Boost midsole adds responsive cushioning for all-day comfort."
        ));
        return product;
    }
}
