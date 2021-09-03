package com.example.blogproject.comment.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {
    @NotBlank
    private String content;
}
