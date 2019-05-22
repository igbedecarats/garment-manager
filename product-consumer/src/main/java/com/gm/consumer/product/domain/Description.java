package com.gm.consumer.product.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "product_description")
@NoArgsConstructor
@AllArgsConstructor
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

    @Type(type = "text")
    private String text;
}
