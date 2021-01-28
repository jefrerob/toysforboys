package be.vdab.toysforboys.services;

import be.vdab.toysforboys.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order>  findAllUnshippedOrders();
}
