package be.vdab.toysforboys.repositories;


import be.vdab.toysforboys.domain.Order;
import be.vdab.toysforboys.domain.OrderDetail;
import be.vdab.toysforboys.domain.Status;
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
            Set<OrderDetail> orderDetails = order.getOrderDetails();
            boolean orderIsOk = true;
            if (order.getStatus() == Status.CANCELLED || order.getStatus() == Status.SHIPPED || orderDetails.isEmpty()){
                orderIsOk = false;
            } else {
                order.setStatusToShippedAndShipDate();
                for (OrderDetail orderDetail : orderDetails) {
                    if (orderDetail.isInStock()) {
                        orderDetail.getProduct().lowerInStockAndInOrder(orderDetail.getOrdered());
                    } else {
                        orderIsOk = false;
                        break;
                    }
                }
            }
            if (orderIsOk == true){
                manager.flush();
            } else {
                failedToShipOrderIds.add(order.getId());
                manager.clear();
            }
    }
        return failedToShipOrderIds;
    }



}
