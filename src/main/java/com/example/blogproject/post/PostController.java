package com.example.blogproject.post;

import com.example.blogproject.post.Post;
import com.example.blogproject.post.PostService;
import com.example.blogproject.post.form.PostForm;
import com.example.blogproject.post.validator.PostFormValidator;

import com.example.blogproject.user.User;
import com.example.blogproject.user.form.UserForm;
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
public class PostController {
    private final PostService postService;
    private final PostFormValidator postFormValidator;

    @InitBinder("postForm")
    public void initBinderPostForm(WebDataBinder webDataBinder){
        webDataBinder.addValidators(postFormValidator);
    }

    public PostController(PostService postService, PostFormValidator postFormValidator) {
        this.postService = postService;
        this.postFormValidator = postFormValidator;
    }

    //포스트 목록 조회 //GET /posts
    @GetMapping("/posts")
    public String index(Model model){
        List<Post> posts = postService.findAll();
        model.addAttribute("posts",posts);
        return "posts/index";
    }

    //포스트 상세 조회 //Get/posts/post_id
    @GetMapping("/posts/{postId}")
    public String show(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);
        model.addAttribute("post",post);

        return "posts/show";
    }

    @GetMapping("posts/new-post")
    public String newPost(Model model){

        model.addAttribute("postForm",new PostForm());
        return "posts/new-post";
    }

    //포ㅗ스트 생성 // POST
    @PostMapping("/new-post")
    public String create(@Valid PostForm postForm, Errors errors){
        if(errors.hasErrors()){
            return "posts/new-post";
        }
        Post post= new Post(
                postForm.getId(),
                postForm.getTitle(),
                postForm.getDescription()
        );
        postService.save(post);
        return "redirect:/posts";
    }

    //포스트 수정 //
    @GetMapping("/posts/edit-post/{postId}")
    public String editPost(@PathVariable Long postId, Model model){
        Post post=postService.findById(postId);
        model.addAttribute("postForm",new PostForm(
                post.getId(),
                post.getTitle(),
                post.getDescription()
        ));
        return "posts/edit-post";
    }
    @PostMapping("/posts/edit-post/{postId}")
    public String editPost(@PathVariable Long postId, PostForm postForm){
        Post post = postService.findById(postId);
        post.setId(postForm.getId());
        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getTitle());

        return "redirect:/posts";
    }
}
