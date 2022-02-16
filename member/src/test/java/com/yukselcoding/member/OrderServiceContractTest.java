package com.yukselcoding.member;


import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.yukselcoding.member.domain.Order;
import com.yukselcoding.member.domain.Orders;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Disabled
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "order-service", port = "8080")
public class OrderServiceContractTest {

    @Autowired
    private OrderServiceClient orderServiceClient;

    @Pact(consumer = "member-service")
    public RequestResponsePact pactOrdersForMemberWithID1AsSellerExists(PactDslWithProvider builder) {

        JSONObject body = new JSONObject();
        JSONArray orders = new JSONArray();
        orders.put(new Order("2", "2", "1", "2"));
        orders.put(new Order("3", "3", "1", "3"));
        try {
            body.put("orders", orders);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return builder.given(
                        "Orders for member 1 as seller exists")
                .uponReceiving("A request to /order/1/seller")
                .path("/order/1/seller")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact();

    }

//    @Pact(consumer = "messaging-app")
//    public RequestResponsePact pactUserDoesNotExist(PactDslWithProvider builder) {
//
//        return builder.given(
//                        "User 2 does not exist")
//                .uponReceiving("A request to /users/2")
//                .path("/users/2")
//                .method("GET")
//                .willRespondWith()
//                .status(404)
//                .toPact();
//    }

    @PactTestFor(pactMethod = "pactOrdersForMemberWithID1AsSellerExists")
    @Test
    public void pactOrdersForMemberWithID1AsSellerExists() {
        Orders orders = orderServiceClient.getOrders("/order/1/seller");
        assertEquals(2, orders.getOrders().size());
    }

//    @PactTestFor(pactMethod = "pactUserDoesNotExist")
//    @Test
//    public void userDoesNotExist() {
//        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
//                userServiceClient.getUser("2"));
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
//    }
}
