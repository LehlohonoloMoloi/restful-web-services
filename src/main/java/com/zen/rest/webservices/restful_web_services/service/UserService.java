package com.zen.rest.webservices.restful_web_services.service;

import com.zen.rest.webservices.restful_web_services.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    User findById(int id);
    void deleteById(int id);
}
