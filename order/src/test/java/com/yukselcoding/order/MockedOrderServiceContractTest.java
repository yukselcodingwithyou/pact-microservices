package com.yukselcoding.order;

import au.com.dius.pact.core.model.annotations.PactFolder;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import com.yukselcoding.order.domain.Order;
import com.yukselcoding.order.domain.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;

@Provider("order-service")
@PactFolder("pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockedOrderServiceContractTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @MockBean
    private OrderService orderService;

    @State("Orders for seller 1 exist")
    public void ordersForSeller1Exist() {
        Orders orders = new Orders(List.of(
                new Order("2", "2", "1", "2"),
                new Order("3", "3", "1", "3"))
        );
        ResponseEntity<Orders> response = new ResponseEntity<>(orders, HttpStatus.OK);
        when(orderService.getBySellerId("1")).thenReturn(response);
    }

    @State("Orders for seller 2 do not exist")
    public void ordersForSeller2DoNotExist() {
        when(orderService.getBySellerId("2")).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}