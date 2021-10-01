package ru.din.test.service.intefaces;

import ru.din.test.entity.UserEntity;

public interface UserService {

    /**
     * Save's userEntity
     *
     * @param userEntity
     * @return Created UserEntity
     */
    UserEntity saveUser(UserEntity userEntity);

    /**
     * Searches user in DB by login
     *
     * @param login login string
     * @return UserEntity
     */
    UserEntity findByLogin(String login);

    /**
     * Searches user in DB by login and password
     *
     * @param login    login string
     * @param password password string
     * @return UserEntity
     */
    UserEntity findByLoginAndPassword(String login, String password);

}
