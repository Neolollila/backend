package com.loizenai.jwtauthentication.message.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

public class SignUpForm {

    @Getter
    @Setter
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;

    @Getter
    @Setter
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastname;

    @Getter
    @Setter
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    @Getter
    @Setter
    private Set<String> role;

    @Getter
    @Setter
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}