package com.zen.rest.webservices.restfulwebservices.repository;

import com.zen.rest.webservices.restfulwebservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
