package be.vdab.toysforboys.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Embeddable
@Access(AccessType.FIELD)
public class OrderDetail {
    private int ordered;
    private BigDecimal priceEach;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productid")
    private Product product;


    protected OrderDetail() {
    }

    public OrderDetail(int ordered, BigDecimal priceEach, Product product) {
        this.ordered = ordered;
        this.priceEach = priceEach;
        this.product = product;
    }
}
