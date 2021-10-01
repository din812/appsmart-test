package ru.din.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerUpdateDto {

    @Schema(description = "Customer title", example = "Alan Shepard", required = true, maximum = "255")
    @NotBlank
    @Size(max = 255)
    private String title;

    @Schema(description = "Deletion flag", example = "false", required = true)
    @NotNull
    private Boolean isDeleted;
}