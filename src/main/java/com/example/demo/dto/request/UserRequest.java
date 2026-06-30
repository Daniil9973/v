package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest{
    @NotNull
    private String name;
    @NotNull
    private Integer age;
    @NotNull
    private String phone;
    @NotNull
    private String password;
}
