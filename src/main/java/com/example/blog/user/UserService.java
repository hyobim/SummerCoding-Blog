package com.example.blog.user;

import com.example.blog.user.form.SignUpForm;
import com.example.blog.user.form.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(Long userId, UserForm userForm) {
        User user = findById(userId);
        user.setName(userForm.getName());
        user.setType(userForm.getType());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }

    public void login(User user, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(user),
                passwordEncoder.encode(password),
                List.of(new SimpleGrantedAuthority(user.getType().name())));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public User createUser(@Valid SignUpForm signUpForm) {
        String encodedPassword = passwordEncoder.encode(signUpForm.getPassword());
        User user = User.builder()
                .username(signUpForm.getUsername())
                .name(signUpForm.getName())
                .password(encodedPassword)
                .type(UserType.ROLE_USER)
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        return new UserAccount(user);
    }
}
