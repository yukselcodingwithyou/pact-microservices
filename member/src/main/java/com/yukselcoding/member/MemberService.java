package com.yukselcoding.member;

import com.yukselcoding.member.domain.Member;
import com.yukselcoding.member.domain.Orders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MemberService {

    private final OrderServiceClient orderServiceClient;

    private static final List<Member> MEMBERS = List.of(
            new Member("1", "veli", "veli@gmail.com"),
            new Member("2", "ali", "ali@gmail.com"),
            new Member("3", "deli", "deli@gmail.com")
    );

    public MemberService(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }


    public ResponseEntity<?> getMember(String id) {
        return MEMBERS.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .map(foundMember -> new ResponseEntity<>(foundMember, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<?> getSellerOrdersOfMember(String id) {
        Orders orders = orderServiceClient.getOrders(String.format("/%s/seller", id));
        return Objects.nonNull(orders) ? new ResponseEntity<>(orders, HttpStatus.OK)
                                       : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getBuyerOrdersOfMember(String id) {
        Orders orders = orderServiceClient.getOrders(String.format("/%s/buyer", id));
        return Objects.nonNull(orders) ? new ResponseEntity<>(orders, HttpStatus.OK)
                                       : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}


