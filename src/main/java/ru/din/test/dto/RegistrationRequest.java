package ru.din.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @Schema(description = "login", example = "appsmart", required = true, maximum = "50")
    @NotBlank
    @Size(max = 50)
    private String login;

    @Schema(description = "login", example = "appsmart", required = true, maximum = "500")
    @NotBlank
    @Size(max = 500)
    private String password;
}
