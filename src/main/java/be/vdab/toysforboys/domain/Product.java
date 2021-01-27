package be.vdab.toysforboys.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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
    @Version
    private int version;

    protected Product(){}

    public Product(String name, String scale, String description, int inStock, int inOrder, BigDecimal price, ProductLine productLine) {
        this.name = name;
        this.scale = scale;
        this.description = description;
        this.inStock = inStock;
        this.inOrder = inOrder;
        this.price = price;
        this.productLine = productLine;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScale() {
        return scale;
    }

    public String getDescription() {
        return description;
    }

    public int getInStock() {
        return inStock;
    }

    public int getInOrder() {
        return inOrder;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void lowerInStockAndInOrder(int numberOrdered){
        if (inOrder - numberOrdered < 0 || inStock - numberOrdered < 0){
            throw new IllegalArgumentException("There is not enough in stock or in order for product: " + name);
        }
        inOrder-= numberOrdered;
        inStock-=numberOrdered;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(name.toUpperCase(), product.name.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toUpperCase());
    }
}
