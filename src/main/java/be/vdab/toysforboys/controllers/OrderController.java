package be.vdab.toysforboys.controllers;

import be.vdab.toysforboys.domain.Order;
import be.vdab.toysforboys.forms.OrdersToShipForm;
import be.vdab.toysforboys.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("setorderstoshipped")
    public ModelAndView setorderstoshipped(OrdersToShipForm form) {
        List<Order> ordersToShip = orderService.findOrdersByIds(form.getOrderIdsToShip());
        if (ordersToShip.isEmpty()) {
            return new ModelAndView("index", "orders", orderService.findAllUnshippedOrders()).addObject(new OrdersToShipForm(null));
        }
        Set<Long> failedToShipOrderIds = new LinkedHashSet<>();

        return new ModelAndView("index", "orders", orderService.findAllUnshippedOrders()).addObject(new OrdersToShipForm(null));
    }
}
