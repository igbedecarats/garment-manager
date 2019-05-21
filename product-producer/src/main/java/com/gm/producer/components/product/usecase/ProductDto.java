package com.gm.producer.components.product.usecase;

import java.io.Serializable;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name", "model_number"})
@ToString(of = {"id", "name", "model_number", "product_type", "meta_data", "pricing_information", "product_description"})
public class ProductDto implements Serializable {

    private String id;

    private String name;

    private String model_number;

    private String product_type;

    private MetadataDto meta_data;

    private PricingDto pricing_information;

    private DescriptionDto product_description;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString(of = {"page_title", "site_name", "description", "keywords", "canonical"})
    public class MetadataDto implements Serializable {
        private String page_title;
        private String site_name;
        private String description;
        private String keywords;
        private String canonical;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString(of = {"standard_price", "standard_price_no_vat", "currentPrice"})
    public class PricingDto implements Serializable {
        private float standard_price;
        private float standard_price_no_vat;
        private float currentPrice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString(of = {"title", "subtitle", "text"})
    public class DescriptionDto implements Serializable {
        private String title;
        private String subtitle;
        private String text;
    }
}
