package ru.din.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.din.test.dto.AuthRequest;
import ru.din.test.dto.AuthResponse;
import ru.din.test.dto.RegistrationRequest;
import ru.din.test.entity.Role;
import ru.din.test.entity.UserEntity;
import ru.din.test.service.impl.AuthServiceImpl;

import javax.validation.Valid;

@RestController
@Tag(name = "Auth", description = "Contains services related to authorization")
@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Returns registered user object UserEntity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest,
                                                   @RequestParam("role")
                                                   @Parameter(description = "Desired user role", required = true)
                                                   Role role) {
        log.debug("registerUser with data {} and role {}", registrationRequest.toString(), role.toString());
        return ResponseEntity.ok(authService.registerUser(registrationRequest, role));
    }

    @PostMapping("/auth")
    @Operation(summary = "Authorize user", description = "Authorizes user and returns bearer token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        log.debug("authorizes user with {}", request);
        return ResponseEntity.ok(authService.auth(request));
    }
}
