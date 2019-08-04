package com.jwtsecurity.project.jwt.example.domain.repository;

import com.jwtsecurity.project.jwt.example.domain.entity.Exposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ExpositionRepository extends JpaRepository<Exposition, BigInteger> {
}
