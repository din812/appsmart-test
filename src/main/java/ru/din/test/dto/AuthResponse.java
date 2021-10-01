package ru.din.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "token", example = "appsmart token", required = true)
    @NotBlank
    private String token;
}
