package be.vdab.toysforboys.repositories;


import be.vdab.toysforboys.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class JpaOrderRepository implements OrderRepository{

    private final EntityManager manager;

    public JpaOrderRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<Order> findAllUnshippedOrders() {
        return manager.createNamedQuery("Order.findAllUnshippedOrders", Order.class)
                .setHint("javax.persistence.loadgraph", manager.createEntityGraph(Order.WITH_CUSTOMER))
                .getResultList();
    }

}
