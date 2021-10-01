package ru.din.test.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.din.test.entity.UserEntity;
import ru.din.test.service.impl.UserServiceImpl;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserServiceImpl userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByLogin(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}
