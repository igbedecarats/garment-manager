package com.gm.storage.components.product.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_description")
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"title", "subtitle", "text"})
@EqualsAndHashCode(of = {"id"})
public class Description implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String title;

    private String subtitle;

    private String text;

    public Description(
            final String id,
            final Product product,
            final String title,
            final String subtitle,
            final String text) {
        this.id = id;
        this.product = product;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }
}
