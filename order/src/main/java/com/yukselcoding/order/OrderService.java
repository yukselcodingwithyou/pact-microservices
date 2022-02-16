package com.yukselcoding.order;


import com.yukselcoding.order.domain.Order;
import com.yukselcoding.order.domain.Orders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final List<Order> ORDERS = List.of(
            new Order("1", "1", "2", "1"),
            new Order("2", "2", "1", "2"),
            new Order("3", "3", "1", "3"),
            new Order("4", "3", "2", "4")
    );

    public ResponseEntity<?> getOrder(String id) {
        return ORDERS.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .map(foundOrder -> new ResponseEntity<>(foundOrder, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Orders> getBySellerId(String sellerId) {
        List<Order> orders = ORDERS.stream().filter(order -> order.getSellerId().equals(sellerId)).collect(Collectors.toList());
        return orders.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(new Orders(orders), HttpStatus.OK);
    }

    public ResponseEntity<?> getByBuyerId(String buyerId) {
        List<Order> orders = ORDERS.stream().filter(order -> order.getBuyerId().equals(buyerId)).collect(Collectors.toList());
        return orders.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(new Orders(orders), HttpStatus.OK);
    }

}


