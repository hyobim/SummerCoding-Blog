package com.example.blogproject.user;

import com.example.blogproject.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

}
