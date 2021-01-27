package be.vdab.toysforboys.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderDetailTest {

    private OrderDetail orderDetail;
    private Product product;

    @BeforeEach
    void beforeEach() {
        product = new Product("test","test","test",10,10, BigDecimal.TEN, new ProductLine());
        orderDetail = new OrderDetail (10,BigDecimal.TEN, product);
    }

    @Test
    void totalPriceSum() {
        assertThat(orderDetail.getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void checkIfProductThatHasEnoughStockIsInStock() {
        assertThat(orderDetail.isInStock()).isTrue();
    }

}