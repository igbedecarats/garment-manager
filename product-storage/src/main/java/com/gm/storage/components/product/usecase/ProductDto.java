package com.gm.storage.components.product.usecase;

import com.gm.storage.components.product.domain.Description;
import com.gm.storage.components.product.domain.Metadata;
import com.gm.storage.components.product.domain.Pricing;
import com.gm.storage.components.product.domain.Product;
import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

    public Product toEntity() {
        Product product = new Product(
                this.id,
                this.name,
                this.model_number,
                this.product_type);
        if (this.meta_data != null) {
            product.setMetaData(
                    new Metadata(
                            product.getId(),
                            product,
                            this.meta_data.page_title,
                            this.meta_data.site_name,
                            this.meta_data.description,
                            Pattern.compile(" ").splitAsStream(this.meta_data.keywords).collect(Collectors.toList()),
                            this.meta_data.canonical));
        }
        if (this.pricing_information != null) {
            product.setPricing(
                    new Pricing(
                            product.getId(),
                            product,
                            this.pricing_information.standard_price,
                            this.pricing_information.standard_price_no_vat,
                            this.pricing_information.currentPrice
                    )
            );
        }
        if (this.product_description != null) {
            product.setProductDescription(
                    new Description(
                            product.getId(),
                            product,
                            this.product_description.title,
                            this.product_description.subtitle,
                            this.product_description.text
                    )
            );
        }
        return product;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString(of = {"page_title", "site_name", "description", "keywords", "canonical"})
    public static class MetadataDto implements Serializable {
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
    public static class PricingDto implements Serializable {
        private float standard_price;
        private float standard_price_no_vat;
        private float currentPrice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString(of = {"title", "subtitle", "text"})
    public static class DescriptionDto implements Serializable {
        private String title;
        private String subtitle;
        private String text;
    }
}
