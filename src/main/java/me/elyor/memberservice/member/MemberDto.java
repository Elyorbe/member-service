package me.elyor.memberservice.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import me.elyor.memberservice.global.validation.constraint.MobilePhone;
import me.elyor.memberservice.global.validation.constraint.Password;
import me.elyor.memberservice.global.validation.constraint.Username;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Builder
    @AllArgsConstructor
    public static class SignupRequest {

        @Username(message = "사용자 아이디는 최소 5자, 최대 20자, 영문소문자, 숫자만 사용 가능합니다")
        public String username;

        @Password(message = "비밀번호는 최소 8자, 최대 20자 영문대소문자, 숫자, 특수문자 2가지 조합 있어야 합니다")
        public String password;

        @NotBlank
        @Email(message = "유효하지 않은 이메일입니다")
        public String email;

        @MobilePhone(message = "전화 번호는 010-XXXX-XXXX 또는 010XXXXXXXX 형식이어야 합니다.")
        public String phoneNumber;

    }

    public static class SignupResponse {

        public Integer id;

        public SignupResponse(Integer id) {
            this.id = id;
        }

    }

    @AllArgsConstructor
    public static class DetailedResponse {

        public String username;
        public String email;
        public String phoneNumber;

    }

}
