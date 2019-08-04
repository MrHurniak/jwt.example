package com.jwtsecurity.project.jwt.example.config.security;

import com.jwtsecurity.project.jwt.example.domain.entity.User;
import com.jwtsecurity.project.jwt.example.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.map(this::toUserDetails).orElseThrow(
                () -> new UsernameNotFoundException("User with login " + login + " wasn't found!"));
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Enum::name).toArray(String[]::new)).build();
    }
}
