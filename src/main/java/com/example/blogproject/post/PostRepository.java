package com.example.blogproject.post;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private HashMap<Long, Post> posts= new HashMap<>();

    public Post findById(Long id){
        return posts.get(id);
    }

    public Boolean existById(Long id){
        return posts.get(id)!=null;
    }
    public Boolean existByTitle(String title){
        return posts.values().stream().anyMatch(post-> post.getTitle().equals(title));
    }

    public List<Post> findAll(){
        return posts.values().stream().collect(Collectors.toList());
    }
    public void save(Post post){
        posts.put(post.getId(),post);
    }
}
