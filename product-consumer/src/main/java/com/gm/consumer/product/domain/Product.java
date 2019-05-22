package com.gm.consumer.product.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name", "modelNumber"})
@ToString(of = {"id", "name", "modelNumber", "productType", "metaData", "pricing", "productDescription"})
public class Product implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String name;

    private String modelNumber;

    private String productType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy="product")
    private Metadata metaData;

    @OneToOne(cascade = CascadeType.ALL, mappedBy="product")
    private Pricing pricing;

    @OneToOne(cascade = CascadeType.ALL, mappedBy="product")
    private Description productDescription;
}
