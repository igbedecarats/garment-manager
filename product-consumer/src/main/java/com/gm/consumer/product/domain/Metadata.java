package com.gm.consumer.product.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_meta_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "pageTitle", "siteName", "description", "keywords", "canonical"})
@EqualsAndHashCode(of = {"id"})
public class Metadata implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String pageTitle;

    private String siteName;

    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_meta_data_keywords")
    private List<String> keywords;

    private String canonical;
}
