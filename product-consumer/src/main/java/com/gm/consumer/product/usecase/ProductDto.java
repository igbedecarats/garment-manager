package com.gm.consumer.product.usecase;

import com.gm.consumer.product.domain.Product;
import java.io.Serializable;
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

    static ProductDto fromEntity(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getProductType(),
                product.getModelNumber(),
                new MetadataDto(
                        product.getMetaData().getPageTitle(),
                        product.getMetaData().getSiteName(),
                        product.getMetaData().getDescription(),
                        String.join(" ", product.getMetaData().getKeywords()),
                        product.getMetaData().getCanonical()
                ),
                new PricingDto(
                        product.getPricing().getStandardPrice(),
                        product.getPricing().getStandardPriceNoVat(),
                        product.getPricing().getCurrentPrice()
                ),
                new DescriptionDto(
                        product.getDescription().getTitle(),
                        product.getDescription().getSubtitle(),
                        product.getDescription().getText()
                )
        );
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
