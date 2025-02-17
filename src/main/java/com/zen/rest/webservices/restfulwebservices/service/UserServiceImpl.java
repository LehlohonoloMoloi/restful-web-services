package com.zen.rest.webservices.restfulwebservices.service;

import com.zen.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.zen.rest.webservices.restfulwebservices.model.Post;
import com.zen.rest.webservices.restfulwebservices.model.User;
import com.zen.rest.webservices.restfulwebservices.repository.PostRepository;
import com.zen.rest.webservices.restfulwebservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with ID %d was not found",id)));
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Post> retrievePostsForUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with ID %d was not found",id)));
        return user.getPosts();
    }

    @Override
    public Post createPostForUser(int id, Post post) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with ID %d was not found",id)));
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Post retrieveUserPostById(int id, int postId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with ID %d was not found",id)));

        return user.getPosts().stream()
                .filter(post -> post.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("Post with ID %d was not found for user with ID %d",postId,id)));
    }

}
