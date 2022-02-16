package com.yukselcoding.order;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.yukselcoding.order.domain.Order;
import com.yukselcoding.order.domain.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@Provider("order-service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PactBroker(host = "localhost", tags = "${pactbroker.tags:prod}")
@Disabled
public class GenericStateWithParameterContractTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @MockBean
    private OrderService orderService;

    @State("default")
    public void toDefaultState(Map<String, Object> params) {
        final boolean ordersExist = (boolean) params.get("ordersExist");
        final String id = (String) params.get("id");
        if (ordersExist) {
            Orders orders = new Orders(List.of(new Order("2", "2", "1", "2"),
                    new Order("3", "3", "1", "3")));
            ResponseEntity<Orders> response = new ResponseEntity<>(orders, HttpStatus.OK);
            when(orderService.getBySellerId(id)).thenReturn(response);
        } else {
            when(orderService.getBySellerId(id)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }


}
