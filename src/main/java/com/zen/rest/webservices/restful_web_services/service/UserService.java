package com.zen.rest.webservices.restful_web_services.service;

import com.zen.rest.webservices.restful_web_services.model.Post;
import com.zen.rest.webservices.restful_web_services.model.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    User findById(int id);
    void deleteById(int id);
    List<Post> retrievePostsForUser(int id);
    Post createPostForUser(int id, @Valid Post post);
    Post retrieveUserPostById(int id, int postId);
}
