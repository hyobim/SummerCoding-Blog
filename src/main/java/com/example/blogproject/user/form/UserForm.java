package com.example.blogproject.user.form;

import com.example.blogproject.user.UserType;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private UserType type;
}
