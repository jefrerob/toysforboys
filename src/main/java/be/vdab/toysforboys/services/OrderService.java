package be.vdab.toysforboys.services;

import be.vdab.toysforboys.domain.Order;

import java.util.List;
import java.util.Set;

public interface OrderService {
    List<Order>  findAllUnshippedOrders();
    List<Order> findOrdersByIds(Set<Long> ids);
}
