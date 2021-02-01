package be.vdab.toysforboys.repositories;

import be.vdab.toysforboys.domain.Order;
import be.vdab.toysforboys.domain.Product;
import be.vdab.toysforboys.domain.ProductLine;
import be.vdab.toysforboys.domain.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"/insertCountry.sql", "/insertCustomer.sql", "/insertOrder.sql","/insertProductLine.sql", "/insertProduct.sql", "/insertOrderDetail.sql"})
@Import(JpaOrderRepository.class)
class JpaOrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaOrderRepository repository;
    private static final String ORDERS = "orders";


    public JpaOrderRepositoryTest(JpaOrderRepository repository) {
        this.repository = repository;
    }

    private long idFromTestUnshippedOrder() {
        return super.jdbcTemplate.queryForObject("select id from orders where comments = 'testUnshipped'", long.class);
    }

    private long idFromTestShippedOrder() {
        return super.jdbcTemplate.queryForObject(
                "select id from orders where comments = 'testShipped'", Long.class);
    }

    @Test
    void findAllUnshippedOrders() {
        var orders = repository.findAllUnshippedOrders();
        assertThat(orders).hasSize(super.countRowsInTableWhere(ORDERS, "status in('PROCESSING','WAITING','RESOLVED','DISPUTED')"))
                .extracting(order->order.getId()).isSorted();
    }

    @Test
    void findOrdersByIds() {
        long id1 = idFromTestShippedOrder();
        long id2 = idFromTestUnshippedOrder();
        var orders = repository.findOrdersByIds(Set.of(id1, id2));
        assertThat(repository.findOrdersByIds(Set.of(id1, id2))).extracting(order->order.getId()).containsOnly(id1, id2).isSorted();
    }

    @Test
    void findOrdersByIdsGivesEmtySetOrdersWhenSearchingWithEmptySetOfIds() {
        assertThat(repository.findOrdersByIds(Set.of())).isEmpty();
    }
    @Test
    void findOrdersByIdsGivesEmtySetOrdersWhenSearchingWithUnexistingOrderIds() {
        assertThat(repository.findOrdersByIds(Set.of(-1L))).isEmpty();
    }

    @Test
    void testingConectionBetweenOrderAndOrderDetailAndProduct(){
        long id1 = idFromTestUnshippedOrder();
        var orders = repository.findOrdersByIds(Set.of(id1));
        var orderDetails = orders.stream().iterator().next().getOrderDetails();
        var productName = orderDetails.iterator().next().getProduct().getName();
        assertThat(productName).isEqualTo("test1");
        assertThat(productName).isNotEqualTo("test2");
    }

    @Test
    void shipOrders(){
        long id1 = idFromTestShippedOrder();
        long id2 = idFromTestUnshippedOrder();
        var orders = repository.findOrdersByIds(Set.of(id1, id2));
        Set<Long> failedToShipOrderIds =  repository.shipOrders(orders);
        orders.stream().forEach(order -> assertThat(order.getStatus()).isEqualByComparingTo(Status.SHIPPED));
        assertThat(failedToShipOrderIds).containsOnly(id1);
    }


}