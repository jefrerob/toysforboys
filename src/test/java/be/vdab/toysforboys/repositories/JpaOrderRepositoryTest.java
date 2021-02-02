package be.vdab.toysforboys.repositories;

import be.vdab.toysforboys.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"/insertCountry.sql", "/insertCustomer.sql", "/insertOrder.sql","/insertProductLine.sql", "/insertProduct.sql", "/insertOrderDetail.sql"})
@Import(JpaOrderRepository.class)
class JpaOrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaOrderRepository repository;
    private static final String ORDERS = "orders";
    private final EntityManager entityManager;


    public JpaOrderRepositoryTest(JpaOrderRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
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
    void findOrdersByIdsGivesEmptySetOrdersWhenSearchingWithEmptySetOfIds() {
        assertThat(repository.findOrdersByIds(Set.of())).isEmpty();
    }
    @Test
    void findOrdersByIdsGivesEmptySetOrdersWhenSearchingWithUnexistingOrderIds() {
        assertThat(repository.findOrdersByIds(Set.of(-1L))).isEmpty();
    }

    @Test
    void testingConnectionBetweenOrderAndOrderDetailAndProduct(){
        long id1 = idFromTestUnshippedOrder();
        var orderDetails = repository.findOrdersByIds(Set.of(id1)).iterator().next().getOrderDetails();
        var productName = orderDetails.iterator().next().getProduct().getName();
        assertThat(productName).isEqualTo("testUnshipped");
        assertThat(productName).isNotEqualTo("testShipped");
    }

    @Test
    void shipOrderSucces(){
        long id = idFromTestUnshippedOrder();
        var order = repository.findOrdersByIds(Set.of(id)).iterator().next();
        String succesToShipOrderId = repository.shipOrder(order);
        entityManager.flush();
        assertThat(order.getStatus()).isEqualByComparingTo(Status.SHIPPED);
        assertThat(succesToShipOrderId).isEqualTo("");
        assertThat(super.jdbcTemplate.queryForObject("select instock from products where name = 'testUnshipped'", Integer.class)).isEqualTo(0);
    }

    @Test
    void shipOrderFail() {
        long id = idFromTestShippedOrder();
        var order = repository.findOrdersByIds(Set.of(id)).iterator().next();
        String failToShipOrderId = repository.shipOrder(order);
        assertThat(order.getStatus()).isEqualByComparingTo(Status.SHIPPED);
        assertThat(failToShipOrderId).isEqualTo(order.getId()+ ", ");
        assertThat(super.jdbcTemplate.queryForObject("select instock from products where name = 'testShipped'", Integer.class)).isEqualTo(10);
    }

    @Test
    void findById () {
        Order order = repository.findById(idFromTestUnshippedOrder()).get();
        assertThat(order.getComments()).isEqualTo("testUnshipped");
        assertThat(order.getCustomer().getName()).isEqualTo("test");
        assertThat(order.getCustomer().getCountry().getName()).isEqualTo("test");

    }
    @Test
    void getTotalOrderPrice(){
        Order order = repository.findById(idFromTestUnshippedOrder()).get();
        assertThat(order.getTotalOrderPrice()).isEqualTo(order.getOrderDetails().iterator().next().getTotalPrice());
    }


}

  /* (ignore this please)
    long id1 = idFromTestShippedOrder();
    long id2 = idFromTestUnshippedOrder();
    var orders = repository.findOrdersByIds(Set.of(id1, id2));
    Set<Long> failedToShipOrderIds = repository.shipOrders(orders);
        orders.forEach(order -> assertThat(order.getStatus()).isEqualByComparingTo(Status.SHIPPED));
    assertThat(failedToShipOrderIds).containsOnly(id1);
    assertThat(super.jdbcTemplate.queryForObject("select instock from products where name = 'testUnshipped'", Integer.class)).isEqualTo(0);
*/