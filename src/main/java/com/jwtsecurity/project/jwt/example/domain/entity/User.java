package com.jwtsecurity.project.jwt.example.domain.entity;

import com.jwtsecurity.project.jwt.example.domain.enumeration.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(nullable = false, length = 100)
    private String login;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
