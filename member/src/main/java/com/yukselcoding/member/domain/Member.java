package com.yukselcoding.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Member {
    private String id;
    private String nickname;
    private String email;
}
