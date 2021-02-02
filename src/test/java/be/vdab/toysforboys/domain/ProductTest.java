package be.vdab.toysforboys.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ProductTest {
    private Product product;

    @BeforeEach
    void beforeEach() {
        product = new Product("test","test","test",10,10, BigDecimal.TEN, new ProductLine());
    }

    @Test
    void loweringInStockAndInOrder() {
        int ordered = 5;
        product.lowerInStockAndInOrder(ordered);
        assertThat(product.getInStock()).isEqualTo(5);
        assertThat(product.getInOrder()).isEqualTo(5);
    }

}