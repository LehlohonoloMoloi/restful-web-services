package com.zen.rest.webservices.restful_web_services.service;

import com.zen.rest.webservices.restful_web_services.exception.UserNotFoundException;
import com.zen.rest.webservices.restful_web_services.model.Post;
import com.zen.rest.webservices.restful_web_services.model.User;
import com.zen.rest.webservices.restful_web_services.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

}
