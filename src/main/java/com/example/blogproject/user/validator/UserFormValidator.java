package com.example.blogproject.user.validator;

import com.example.blogproject.user.UserRepository;
import com.example.blogproject.user.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserFormValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm)target;
        if(userRepository.existById(userForm.getId())){
            //에러 반환
            errors.rejectValue("id","exists-value","이미 존재하는 ID 입니다.");
        }
        if(userRepository.existByName(userForm.getName())){
            //에러 반환
            errors.rejectValue("name","exists-value","이미 존재하는 이름 입니다.");
        }
    }
}
