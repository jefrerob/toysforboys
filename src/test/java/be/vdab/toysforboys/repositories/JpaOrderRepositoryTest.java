package be.vdab.toysforboys.repositories;

import be.vdab.toysforboys.domain.Order;
import be.vdab.toysforboys.domain.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"/insertCountry.sql", "/insertCustomer.sql", "/insertOrder.sql"})
@Import(JpaOrderRepository.class)
class JpaOrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaOrderRepository repository;
    private final EntityManager manager;
    private static final String ORDERS = "orders";


    public JpaOrderRepositoryTest(JpaOrderRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
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


}