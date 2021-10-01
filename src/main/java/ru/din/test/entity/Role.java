package ru.din.test.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Schema(name = "Role", defaultValue = "ROLE_ADMIN")
public enum Role {
    ROLE_ADMIN,
    ROLE_USER
}
