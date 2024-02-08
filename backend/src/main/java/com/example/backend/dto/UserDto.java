package com.example.backend.dto;

import com.example.backend.validation.ValidEmailDomain;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    @ValidEmailDomain(message = "@ewhain.net만 회원가입 가능합니다.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6글자 이상, 20글자 이하여야 합니다.")
    private String password;
}
