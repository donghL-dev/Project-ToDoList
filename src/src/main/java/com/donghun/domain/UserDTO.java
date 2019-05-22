package com.donghun.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author dongh9508
 * @since  2019-03-31
 */
@Getter
@Setter
public class UserDTO {

        @NotBlank(message = "아이디를 입력하세요.")
        @Length(min = 4, max=12)
        @Pattern(regexp = "^[a-z0-9]{4,12}$", message = "아이디 구성을 올바르게 하십시오.")
        private String id;

        @NotBlank(message = "비밀번호를 입력하세요.")
        @Length(min = 5, max = 22)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{5,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
        private String password;

        @NotBlank(message = "이메일이 비어있습니다.")
        @Email(message = "유효한 이메일이 아닙니다.")
        private String email;
}
