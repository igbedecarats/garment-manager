package com.gm.consumer.product.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_pricing")
@NoArgsConstructor
@AllArgsConstructor
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
}
