package com.jwtsecurity.project.jwt.example.domain.repository;

import com.jwtsecurity.project.jwt.example.domain.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface HallRepository extends JpaRepository<Hall, BigInteger> {
}
