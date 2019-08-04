package com.jwtsecurity.project.jwt.example.domain.repository;

import com.jwtsecurity.project.jwt.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {

    Optional<User> findByLogin(String login);
}