package be.vdab.toysforboys.controllers;


import be.vdab.toysforboys.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {
    private final OrderService orderService;

    public IndexController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ModelAndView orders() {
        return new ModelAndView("index", "orders", orderService.findAllUnshippedOrders());
    }

}
