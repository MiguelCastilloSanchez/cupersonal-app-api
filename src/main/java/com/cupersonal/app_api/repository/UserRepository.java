package com.cupersonal.app_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.cupersonal.app_api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    UserDetails findByEmail(String email);
    UserDetails findByName(String name);
}
