package ru.din.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDto {

    @Schema(description = "Customer id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    @NotNull
    //@JsonIgnore
    private UUID id;

    @Schema(description = "Customer title", example = "Alan Shepard", required = true, maximum = "255")
    @NotBlank
    @Size(max = 255)
    private String title;

    @Schema(description = "Deletion flag", example = "false", required = true)
    @NotNull
    private Boolean isDeleted;

    @Schema(description = "Creation timestamp", required = true)
    @NotNull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss") doesn't work with OpenAPI, worked in swagger 2
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Schema(description = "Modification timestamp", required = true)
    @NotNull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Builder.Default
    private LocalDateTime modifiedAt = LocalDateTime.now();
}