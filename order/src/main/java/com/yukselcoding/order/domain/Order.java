package com.yukselcoding.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private String id;
    private String buyerId;
    private String sellerId;
    private String productId;
}
