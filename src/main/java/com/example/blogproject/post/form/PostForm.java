package com.example.blogproject.post.form;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostForm {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}


