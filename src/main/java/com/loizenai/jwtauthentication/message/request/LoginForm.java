package com.loizenai.jwtauthentication.message.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginForm {

    @Getter
    @Setter
    @NotBlank
    @Size(min=3, max = 60)
    private String username;

    @Getter
    @Setter
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


}