package com.zen.rest.webservices.restful_web_services.controller;

import com.zen.rest.webservices.restful_web_services.model.Post;
import com.zen.rest.webservices.restful_web_services.model.User;
import com.zen.rest.webservices.restful_web_services.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

     @GetMapping("/v1/users")
    public List<User> retrieveAllUsers() {
         return userService.findAll();
     }

    @GetMapping("/v1/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = userService.findById(id);

        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkBuilder.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/v1/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
         User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

         return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/v1/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteById(id);
    }

    @GetMapping("/v1/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        return userService.retrievePostsForUser(id);
    }

}
