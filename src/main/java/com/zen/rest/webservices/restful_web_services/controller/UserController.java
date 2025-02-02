package com.zen.rest.webservices.restful_web_services.controller;

import com.zen.rest.webservices.restful_web_services.exception.UserNotFoundException;
import com.zen.rest.webservices.restful_web_services.model.User;
import com.zen.rest.webservices.restful_web_services.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

     @GetMapping("/users")
    public List<User> retrieveAllUsers() {
         return userService.findAll();
     }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userService.findById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("User with ID %d was not found",id));
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
         User savedUser = userService.save(user);

//         URI location = URI.create(String.format("/users/%d", savedUser.getId()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

         return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteById(id);
    }

}
