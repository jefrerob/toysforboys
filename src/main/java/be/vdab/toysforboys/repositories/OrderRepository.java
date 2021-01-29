package be.vdab.toysforboys.repositories;

import be.vdab.toysforboys.domain.Order;

import java.util.List;
import java.util.Set;

public interface OrderRepository {
    List<Order> findAllUnshippedOrders();
    List<Order> findOrdersByIds(Set<Long> ids);
}
