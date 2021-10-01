package ru.din.test.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.din.test.config.jwt.JwtProvider;
import ru.din.test.dto.AuthRequest;
import ru.din.test.dto.AuthResponse;
import ru.din.test.dto.RegistrationRequest;
import ru.din.test.entity.Role;
import ru.din.test.entity.UserEntity;
import ru.din.test.repository.RoleRepository;
import ru.din.test.service.intefaces.AuthService;

import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public UserEntity registerUser(RegistrationRequest registrationRequest, Role role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(registrationRequest.getPassword());
        userEntity.setLogin(registrationRequest.getLogin());
        userEntity.setRoleEntity(roleRepository.findByName(role == Role.ROLE_ADMIN ? Role.ROLE_ADMIN.name() : Role.ROLE_USER.name()));
        userService.saveUser(userEntity);

        return userEntity;
    }

    @Transactional(readOnly = true)
    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        UserEntity userEntity = userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
        if (userEntity == null) {
            throw new NoSuchElementException("user doesn't exist");
        }
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return new AuthResponse(token);
    }
}
