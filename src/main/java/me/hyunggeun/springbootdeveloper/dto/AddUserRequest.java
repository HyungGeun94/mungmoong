package me.hyunggeun.springbootdeveloper.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {


    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(min=5, max=20, message = "비밀번호는5~20자 사이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
