package com.example.blogproject.post;

import com.example.blogproject.comment.Comment;
import com.example.blogproject.comment.CommentService;
import com.example.blogproject.comment.form.CommentForm;
import com.example.blogproject.post.form.PostForm;

import com.example.blogproject.user.CurrentUser;
import com.example.blogproject.user.User;
import com.example.blogproject.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    //포스트 목록 조회 //GET /posts
    @GetMapping("/posts")
    public String index(Model model){
        model.addAttribute("posts", postService.findAll());
        return "posts/index";
    }

    //포스트 상세 조회 //Get/posts/post_id
    @GetMapping("/posts/{postId}")
    public String show(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);
        model.addAttribute("post",post);
        model.addAttribute("commentForm",new CommentForm());
        return "posts/show";
    }

    @GetMapping("/new-post")
    public String newPost(Model model){

        model.addAttribute("postForm",new PostForm());
        return "posts/new-post";
    }

    //포ㅗ스트 생성 // POST
    @PostMapping("/new-post")
    public String create(@CurrentUser User user,@Valid PostForm postForm, Errors errors){
        if(errors.hasErrors()){
            return "posts/new-post";
        }

        postService.create(postForm,user);
        return "redirect:/posts";
    }

    //포스트 수정 //
    @GetMapping("/edit-post/{postId}")
    public String editPost(@PathVariable Long postId, Model model){
        Post post=postService.findById(postId);
        model.addAttribute("post",post);
        PostForm postForm=new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setDescription(post.getDescription());
        model.addAttribute("postForm",postForm);
        return "posts/edit-post";
    }
    @PostMapping("/edit-post/{postId}")
    public String editPost(@PathVariable Long postId, PostForm postForm){
        postService.update(postId,postForm);
        return "redirect:/posts";
    }
    @PostMapping("/posts/{postId}/new-comment")
    public String create(@CurrentUser User user, @PathVariable Long postId, @Valid CommentForm commentForm) {
        Post post = postService.findById(postId);
        Comment comment = Comment.builder()
                .content(commentForm.getContent())
                .post(post)
                .user(user)
                .build();
        commentService.create(comment);

        return "redirect:/posts/" + post.getId();
    }
}
