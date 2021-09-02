package com.example.blog.user;

import com.example.blog.user.form.LoginForm;
import com.example.blog.user.form.SignUpForm;
import com.example.blog.user.form.UserForm;
import com.example.blog.user.validator.SignUpFormValidator;
import com.example.blog.user.validator.UserFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFormValidator userFormValidator;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("userForm")
    public void initBinderUserForm(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userFormValidator);
    }

    @InitBinder("signUpForm")
    public void initBinderSignUpForm(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());

        return "user/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "user/sign-up";
        }

        User user = userService.createUser(signUpForm);
        userService.login(user, signUpForm.getPassword());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());

        return "user/login";
    }

//    @PostMapping("/login")
//    public String login(@Valid LoginForm loginForm) {
//        User user = userService.findByUsername(loginForm.getNickname());
//        userService.login(user, loginForm.getPassword());
//
//        return "redirect:/";
//    }

    @GetMapping("/users")
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "user/index";
    }

    @GetMapping("/users/{userId}")
    public String show(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);

        return "user/show";
    }

    @GetMapping("/new-user")
    public String newUser(Model model) {
        model.addAttribute("userForm", new UserForm());

        return "user/new";
    }

    @PostMapping("/new-user")
    public String create(@Valid UserForm userForm, Errors errors) {
        if (errors.hasErrors()) {
            return "user/new";
        }

        User user = User.builder()
                .name(userForm.getName())
                .username(userForm.getName())
                .type(UserType.ROLE_USER)
                .password("root")
                .build();
        userService.save(user);

        return "redirect:/users";
    }

    @GetMapping("/edit-user/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("userForm", new UserForm(
                user.getId(),
                user.getName(),
                user.getType()
        ));

        return "user/edit";
    }

    @PostMapping("/edit-user/{userId}")
    public String editUser(@PathVariable Long userId, UserForm userForm) {
        userService.update(userId, userForm);
        return "redirect:/users";
    }
}
