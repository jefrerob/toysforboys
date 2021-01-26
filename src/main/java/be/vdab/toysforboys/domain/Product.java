package be.vdab.toysforboys.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NamedEntityGraph(name = Product.WITH_PRODUCTLINE,
        attributeNodes = @NamedAttributeNode("productLine") )
public class Product {
    public static final String WITH_PRODUCTLINE = "Product.withProductLine";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String scale;
    private String description;
    private int inStock;
    private int inOrder;
    private BigDecimal price;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productlineid")
    private ProductLine productLine;
}
