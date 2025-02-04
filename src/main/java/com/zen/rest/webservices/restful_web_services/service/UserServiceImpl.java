package com.zen.rest.webservices.restful_web_services.service;

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
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

}
