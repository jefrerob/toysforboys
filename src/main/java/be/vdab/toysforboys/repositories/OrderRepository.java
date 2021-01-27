package be.vdab.toysforboys.repositories;

import be.vdab.toysforboys.domain.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAllUnshippedOrders();
}
