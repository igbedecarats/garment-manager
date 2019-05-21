package com.gm.storage.components.product.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_pricing")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"standardPrice", "standardPriceNoVat", "currentPrice"})
public class Pricing implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private float standardPrice;

    private float standardPriceNoVat;

    private float currentPrice;

    public Pricing(
            final String id,
            final Product product,
            final float standardPrice,
            final float standardPriceNoVat,
            final float currentPrice) {
        this.id = id;
        this.product = product;
        this.standardPrice = standardPrice;
        this.standardPriceNoVat = standardPriceNoVat;
        this.currentPrice = currentPrice;
    }
}
