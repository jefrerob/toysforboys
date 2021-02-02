package be.vdab.toysforboys.controllers;

import be.vdab.toysforboys.domain.Customer;
import be.vdab.toysforboys.domain.Order;
import be.vdab.toysforboys.forms.OrdersToShipForm;
import be.vdab.toysforboys.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("{id}")
    public ModelAndView order(@PathVariable long id) {
        var modelAndView = new ModelAndView("order");
        orderService.findById(id).ifPresent(order -> {
            Customer customer = order.getCustomer();
            modelAndView
                    .addObject(order)
                    .addObject("customer", customer)
                    .addObject("country", customer.getCountry())
                    .addObject("orderdetails", order.getOrderDetails());
        });
        return modelAndView;
    }


    @PostMapping("setorderstoshipped")
    public ModelAndView setOrdersToShipped(OrdersToShipForm form) {
        List<Order> ordersToShip = orderService.findOrdersByIds(form.getOrderIdsToShip());
        if (!ordersToShip.isEmpty()) {
            StringBuilder failedToShipOrderIds = new StringBuilder();
            ordersToShip.forEach(order -> failedToShipOrderIds.append(orderService.shipOrder(order)));
           if (failedToShipOrderIds.length() > 0) {
               return new ModelAndView("index", "orders", orderService.findAllUnshippedOrders()).addObject(new OrdersToShipForm(null))
                       .addObject("failedToShipOrderIds", failedToShipOrderIds.toString());
           }
        }

        return new ModelAndView("redirect:/");
    }
}
