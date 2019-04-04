package com.donghun.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author dongh9508
 * @since  2019-03-31
 */
@Getter
@Setter
public class UserDTO {

        @NotBlank(message = "아이디를 입력하세요.")
        private String id;

        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;

        @NotBlank(message = "이메일이 비어있습니다.")
        @Email(message = "유효한 이메일이 아닙니다.")
        private String email;
}
