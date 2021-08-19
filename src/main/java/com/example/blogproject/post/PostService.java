package com.example.blogproject.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    public Post findById(Long id){
        return this.postRepository.findById(id);
    }
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public void save(Post post){
        postRepository.save(post);
    }
}
