package com.yukselcoding.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        return memberService.getMember(id);
    }

    @GetMapping("/{id}/seller")
    public ResponseEntity<?> getSellerOrdersOfMember(@PathVariable("id") String id) {
        return memberService.getSellerOrdersOfMember(id);
    }

    @GetMapping("/{id}/buyer")
    public ResponseEntity<?> getBuyerOrdersOfMember(@PathVariable("id") String id) {
        return memberService.getBuyerOrdersOfMember(id);
    }

}
