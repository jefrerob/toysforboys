package be.vdab.toysforboys.repositories;


import be.vdab.toysforboys.domain.Order;
import be.vdab.toysforboys.domain.OrderDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<Order> findOrdersByIds(Set<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        return manager.createQuery("select o from Order o where  o.id in(:orderids) order by o.id", Order.class).setParameter("orderids", ids).getResultList();
    }

    @Override
    public Set<Long> shipOrders(List<Order> orders) {
        Set<Long> failedToShipOrderIds = new LinkedHashSet<>();
        for (Order order : orders) {
            boolean orderIsOk = true;
            while (orderIsOk == true) {
                for (OrderDetail orderDetail : order.getOrderDetails()) {
                    if (orderDetail.isInStock()) {
                        orderDetail.getProduct().lowerInStockAndInOrder(orderDetail.getOrdered());
                    } else {
                        manager.getTransaction().rollback();
                        orderIsOk = false;
                        failedToShipOrderIds.add(order.getId());
                    }
                }
            }
    }
        return Set.of(); //placeholder
    }



}
