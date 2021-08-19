package com.example.blogproject.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private HashMap<Long,User> users= new HashMap<>();

    public User findById(Long id){
        return users.get(id);
    }

    public Boolean existById(Long id){
        return users.get(id)!=null;
    }
    public Boolean existByName(String name){
        return users.values().stream().anyMatch(user-> user.getName().equals(name));
    }

    public List<User> findAll(){
        return users.values().stream().collect(Collectors.toList());
    }
    public void save(User user){
        users.put(user.getId(),user);
    }
}
