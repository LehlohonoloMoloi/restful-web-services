package com.zen.rest.webservices.restfulwebservices.service;

import com.zen.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.zen.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.zen.rest.webservices.restfulwebservices.model.Post;
import com.zen.rest.webservices.restfulwebservices.model.User;
import com.zen.rest.webservices.restfulwebservices.repository.PostRepository;
import com.zen.rest.webservices.restfulwebservices.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, postRepository);
    }

    @Test
    void canFindAllUsers() {
        // when
        userService.findAll();
        // then
        // verify that the findAll method was called
        verify(userRepository).findAll();
    }

    @Test
    void canSaveUser() {
        // given
        User user = new User();
        user.setName("John Doe");
        user.setBirthDate(LocalDate.now().minusYears(20));

        // when
        userService.save(user);

        // then
        // verify that the save method was called with the same user object
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        // assert that the user object passed to the save method is the same as the user object created
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canFindUserById() {
        // given
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setBirthDate(LocalDate.parse("2000-01-01"));

        given(userRepository.findById(1)).willReturn(Optional.of(user));

        // when and then
        assertThatCode(() -> userService.findById(user.getId())).doesNotThrowAnyException();
    }

    @Test
    void cannotFindUserById() {
        // given
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setBirthDate(LocalDate.parse("2000-01-01"));

        given(userRepository.findById(1)).willReturn(Optional.empty());

        // when and then
        assertThatThrownBy(() -> userService.findById(user.getId()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with ID 1 was not found");


    }

    @Test
    void canDeleteUserById() {
        //when
        userService.deleteById(1);
        //then
        verify(userRepository).deleteById(1);
    }

    @Test
    void canRetrievePostsForUser() {
        // given
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setBirthDate(LocalDate.parse("2000-01-01"));
        Post post = new Post();
        post.setId(1);
        post.setDescription("Post 1");
        user.setPosts(List.of(post));

        given(userRepository.findById(1)).willReturn(Optional.of(user));

        // when
        List<Post> posts = userService.retrievePostsForUser(1);

        // then
        assertThat(posts).contains(post);
    }

    @Test
    void cannotRetrievePostsForUser() {
        // given
        given(userRepository.findById(1)).willReturn(Optional.empty());

        // when and then
        assertThatThrownBy(() -> userService.retrievePostsForUser(1))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with ID 1 was not found");
    }

    @Test
    void canCreatePostForUser() {
        // given
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setBirthDate(LocalDate.parse("2000-01-01"));
        Post post = new Post();
        post.setId(1);
        post.setDescription("Post 1");

        given(userRepository.findById(1)).willReturn(Optional.of(user));

        // when
        userService.createPostForUser(1, post);

        // then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());

        Post capturedPost = postArgumentCaptor.getValue();
        // assert that the user object passed to the save method is the same as the user object created
        assertThat(capturedPost).isEqualTo(post);
    }

    @Test
    void cannotCreatePostForUser() {
        // given
        Post post = new Post();
        post.setId(1);
        post.setDescription("Post 1");

        given(userRepository.findById(1)).willReturn(Optional.empty());

        // when and then
        assertThatThrownBy(() -> userService.createPostForUser(1, post))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with ID 1 was not found");

        verify(postRepository, org.mockito.Mockito.never()).save(post);
    }

    @Test
    void canRetrieveUserPostById() {
        // given
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setBirthDate(LocalDate.parse("2000-01-01"));
        Post post = new Post();
        post.setId(1);
        post.setDescription("Post 1");
        user.setPosts(List.of(post));

        given(userRepository.findById(1)).willReturn(Optional.of(user));

        // when
        Post retrievedPost = userService.retrieveUserPostById(1, 1);

        // then
        assertThat(retrievedPost).isEqualTo(post);
    }

    @Test
    void cannotRetrieveUserPostByIdForInvalidUserId() {
        // given
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setBirthDate(LocalDate.parse("2000-01-01"));
        Post post = new Post();
        post.setId(1);
        post.setDescription("Post 1");
        user.setPosts(List.of(post));

        given(userRepository.findById(1)).willReturn(Optional.empty());

        // when and then
        assertThatThrownBy(() -> userService.retrieveUserPostById(1, 1))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with ID 1 was not found");
    }

    @Test
    void cannotRetrieveUserPostByIdForInvalidUserIdForInvalidPostId() {
        // given
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setBirthDate(LocalDate.parse("2000-01-01"));
        Post post = new Post();
        post.setId(1);
        post.setDescription("Post 1");
        user.setPosts(List.of(post));

        given(userRepository.findById(1)).willReturn(Optional.of(user));

        // when and then
        assertThatThrownBy(() -> userService.retrieveUserPostById(1, 2))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessageContaining("Post with ID 2 was not found for user with ID 1");
    }
}