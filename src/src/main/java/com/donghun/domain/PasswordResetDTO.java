package com.donghun.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author dongh9508
 * @since  2019-06-14
 */
@Getter
@Setter
public class PasswordResetDTO {

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 5, max = 22)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{5,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
    private String password;

    @NotBlank(message = "비밀번호 일치 여부를 확인하세요.")
    @Length(min = 5, max = 22)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{5,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
    private String confirmPassword;

    @NotBlank(message = "올바르지 않은 페이지 접근입니다.")
    private String token;
}
