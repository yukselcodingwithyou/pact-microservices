package com.yukselcoding.member;

import com.yukselcoding.member.domain.Order;
import com.yukselcoding.member.domain.Orders;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OrderServiceClient {

    private final WebClient webClient;

    public OrderServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Orders getOrders(String path) {
        return webClient
                .get()
                .uri(path)
                .retrieve()
                .bodyToMono(Orders.class)
                .block();
    }
}
