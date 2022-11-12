package me.elyor.memberservice.member;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Builder
public class Member {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
