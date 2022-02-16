package com.yukselcoding.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/{id}/buyer")
    public ResponseEntity<?> getByBuyerId(@PathVariable("id") String buyerId) {
        return orderService.getByBuyerId(buyerId);
    }

    @GetMapping("/{id}/seller")
    public ResponseEntity<?> getBySellerId(@PathVariable("id") String buyerId) {
        return orderService.getBySellerId(buyerId);
    }
}
