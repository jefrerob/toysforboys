package be.vdab.toysforboys.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class OrderDetail {
    private int ordered;
    private BigDecimal priceEach;
    @ManyToOne(optional = false)
    @JoinColumn(name = "productid")
    private Product product;

    protected OrderDetail(){}

    public OrderDetail(int ordered, BigDecimal priceEach, Product product) {
        this.ordered = ordered;
        this.priceEach = priceEach;
        this.product = product;
    }

    public int getOrdered() {
        return ordered;
    }
    public BigDecimal getPriceEach() {
        return priceEach;
    }
    public Product getProduct() {
        return product;
    }

    public BigDecimal getTotalPrice() {
        return BigDecimal.valueOf(ordered).multiply(priceEach);
    }

    public boolean isInStock() {
        return ordered <= product.getInStock();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;
        OrderDetail that = (OrderDetail) o;
        return product.getName().toUpperCase().equals(that.product.getName().toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getName().toUpperCase());
    }
}
