package com.example.blogproject.post.validator;

import com.example.blogproject.post.PostRepository;
import com.example.blogproject.post.form.PostForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PostFormValidator implements Validator {
    private final PostRepository postRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostForm postForm = (PostForm)target;
        if(postRepository.existById(postForm.getId())){
            //에러 반환
            errors.rejectValue("id","exists-value","이미 존재하는 ID 입니다.");
        }
        if(postRepository.existByTitle(postForm.getTitle())){
            //에러 반환
            errors.rejectValue("title","exists-value","이미 존재하는 제목 입니다.");
        }
    }
}
