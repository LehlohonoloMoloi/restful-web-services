package com.zen.rest.webservices.restful_web_services.repository;

import com.zen.rest.webservices.restful_web_services.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
