package com.donghun.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author dongh9508
 * @since  2019-06-10
 */
@Getter
@Setter
public class FindPasswordDTO {


    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 5, max = 22)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{5,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
    private String password;

    @NotBlank(message = "비밀번호 일치 여부를 확인하세요.")
    @Length(min = 5, max = 22)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{5,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
    private String confirmPassword;
}
