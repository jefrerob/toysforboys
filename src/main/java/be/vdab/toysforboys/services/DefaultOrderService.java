package be.vdab.toysforboys.services;


import be.vdab.toysforboys.domain.Order;
import be.vdab.toysforboys.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DefaultOrderService implements OrderService{

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllUnshippedOrders(){
        return orderRepository.findAllUnshippedOrders();
    }



}
