package com.jwtsecurity.project.jwt.example.service;

import com.google.common.collect.ImmutableSet;
import com.jwtsecurity.project.jwt.example.config.security.JwtProvider;
import com.jwtsecurity.project.jwt.example.domain.dto.LoginDto;
import com.jwtsecurity.project.jwt.example.domain.dto.RegistrationDto;
import com.jwtsecurity.project.jwt.example.domain.entity.User;
import com.jwtsecurity.project.jwt.example.domain.enumeration.Role;
import com.jwtsecurity.project.jwt.example.domain.mappers.UserMapper;
import com.jwtsecurity.project.jwt.example.domain.repository.UserRepository;
import com.jwtsecurity.project.jwt.example.exceptions.AuthenticationException;
import com.jwtsecurity.project.jwt.example.exceptions.NotUniqueLoginException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @InjectMocks private UserService service;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtProvider jwtProvider;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Spy  private UserMapper userMapper = new UserMapper();

    private static final BigInteger ID = BigInteger.ONE;
    private static final String LOGIN = "Login";
    private static final String PASSWORD = "Password";
    private static final String EMAIL = "some@email.com";
    private static final Set<Role> ROLES = ImmutableSet.of(Role.USER);
    private static final String TOKEN = "TOKEN";

    private static RegistrationDto registrationDto;
    private static LoginDto loginDto;
    private static User user;

    @BeforeAll
    static void setUp() {
        registrationDto = new RegistrationDto().setLogin(LOGIN)
                .setPassword(PASSWORD)
                .setEmail(EMAIL);
        loginDto = new LoginDto().setLogin(LOGIN)
                .setPassword(PASSWORD);
        user = new User().setId(ID)
                .setLogin(LOGIN)
                .setPassword(PASSWORD)
                .setEmail(EMAIL)
                .setRoles(ROLES);
    }

    @Test
    void tryRegisterWhenLoginAlreadyExistInDBAndTryToRegister() {
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(NotUniqueLoginException.class,
                () -> service.registerUser(registrationDto));
        assertTrue(exception.getMessage().contains(LOGIN));
    }

    @Test
    void tryRegisterWhenLoginIsUniqueAndTryToRegister() {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(passwordEncoder.encode(anyString())).thenReturn(PASSWORD);

        service.registerUser(registrationDto);

        verify(userRepository).save(userCaptor.capture());
        assertEquals(user, userCaptor.getValue().setId(ID));
        verify(passwordEncoder).encode(eq(PASSWORD));
        verify(userMapper).dtoToUser(eq(registrationDto));
    }

    @Test
    void tryLoginWhenRepositionReturnEmptyByLogin() {
        RuntimeException exception = assertThrows(AuthenticationException.class, () -> service.createUserToken(loginDto));

        assertTrue(exception.getMessage().contains(loginDto.getLogin()));
        verify(userRepository).findByLogin(LOGIN);
        verify(jwtProvider, never()).createToken(anyString(), anyCollection());
    }

    @Test
    void tryLoginWhenRepositoryFindUserAndPasswordIsCorrect() {
        Authentication authentication = mock(Authentication.class);
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getAuthorities()).thenReturn(Collections.emptySet());
        when(jwtProvider.createToken(anyString(), anyCollection())).thenReturn(TOKEN);

        String userServiceToken = service.createUserToken(loginDto);

        assertEquals(TOKEN, userServiceToken);
        verify(jwtProvider).createToken(eq(LOGIN), anyCollection());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void tryLoginWhenRepositoryFindUserAndPasswordIsInvalid() {
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        RuntimeException exception = assertThrows(AuthenticationException.class,
                () -> service.createUserToken(loginDto));

        assertEquals("Wrong password!", exception.getMessage());
        verify(jwtProvider, never()).createToken(anyString(), anyCollection());
    }
}