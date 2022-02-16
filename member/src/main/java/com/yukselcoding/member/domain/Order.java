package com.yukselcoding.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private String id;
    private String buyerId;
    private String sellerId;
    private String productId;
}


