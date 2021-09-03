package com.example.blogproject.post;

import com.example.blogproject.comment.Comment;
import com.example.blogproject.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment>comments;

    public void addComment(Comment comment){
        this.comments.add(comment);
    }
}
