package com.example.blogproject.post.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostForm {
    @NotNull(message = "no null")
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}


