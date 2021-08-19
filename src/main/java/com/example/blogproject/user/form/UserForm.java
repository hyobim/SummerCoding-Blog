package com.example.blogproject.user.form;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    @NotNull(message = "no null")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
}
