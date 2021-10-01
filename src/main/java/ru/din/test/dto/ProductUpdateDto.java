package ru.din.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductUpdateDto {

    @Schema(description = "Product title", example = "Alan Shepard", required = true, maximum = "255")
    @NotBlank
    @Size(max = 255)
    private String title;

    @Schema(description = "Product description", example = "Some kind of product", required = false, maximum = "1024")
    @Size(max = 1024)
    private String description;

    @Schema(description = "Price in BigDecimal(10,2)", example = "false", required = true)
    @NotNull
    @Builder.Default
    @DecimalMin(value = "0.0")
    @Digits(integer=8, fraction=2)
    private BigDecimal price = BigDecimal.ZERO.setScale(10, RoundingMode.HALF_EVEN);

    @Schema(description = "Deletion flag", example = "false", required = true)
    @NotNull
    private Boolean isDeleted;

}