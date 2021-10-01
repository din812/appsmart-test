package ru.din.test.service.intefaces;

import ru.din.test.dto.AuthRequest;
import ru.din.test.dto.AuthResponse;
import ru.din.test.dto.RegistrationRequest;
import ru.din.test.entity.Role;
import ru.din.test.entity.UserEntity;

public interface AuthService {

    /**
     * Registers userEntity with provided data and returns saved entity
     *
     * @param registrationRequest Registration data
     * @param role                Role enum
     * @return UserEntity
     */
    UserEntity registerUser(RegistrationRequest registrationRequest, Role role);

    /**
     * Authorizes user with auth request data
     *
     * @param authRequest auth data
     * @return AuthResponse
     */
    AuthResponse auth(AuthRequest authRequest);

}
