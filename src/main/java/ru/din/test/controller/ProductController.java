package ru.din.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.din.test.dto.ProductDto;
import ru.din.test.dto.ProductUpdateDto;
import ru.din.test.service.intefaces.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(value = "v1/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Products related services")
@Slf4j
@CrossOrigin
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("customers/{customerId}/products")
    @Operation(summary = "Returns paginated list of all customer products", description = "Returns customer product list in basic Page spring object, filters deleted users. Sorted in ascending order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<Page<ProductDto>> getProducts(@Parameter(description = "Customer ID")
                                                        @PathVariable("customerId") @NotNull UUID customerId,
                                                        @Parameter(description = "Limit per page")
                                                        @RequestParam(defaultValue = "10")
                                                        @Valid @NotEmpty @Min(1) int limit,
                                                        @Parameter(description = "Page number")
                                                        @RequestParam(defaultValue = "1")
                                                        @Valid @NotEmpty @Min(1) int page) {
        log.debug("getProducts limit {} page {}", limit, page);
        return ResponseEntity.ok(productService.getProductsList(customerId, limit, page));
    }

    @PostMapping("customers/{customerId}/products")
    @Operation(summary = "Creates new product for customer", description = "Creates new product for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<ProductDto> createProduct(@Parameter(description = "Customer ID")
                                                    @PathVariable("customerId") @NotNull UUID customerId,
                                                    @Parameter(description = "Product DTO")
                                                    @RequestBody @Valid @NotNull ProductUpdateDto productUpdateDto) {
        log.debug("createProduct for customer {}", customerId);
        return ResponseEntity.ok(productService.createProduct(customerId, productUpdateDto));
    }


    @DeleteMapping("products/{productId}")
    @Operation(summary = "Delete product", description = "Completely deletes product from database by ID, use put/{productId} in order to change is_deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<Void> deleteProduct(@Parameter(description = "Product ID")
                                              @PathVariable("productId") @NotNull UUID productId) {
        log.debug("deleteProduct {}", productId);
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("products/{productId}")
    @Operation(summary = "Returns product by UUID", description = "Returns product DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<ProductDto> getProduct(@Parameter(description = "Product ID")
                                                 @PathVariable("productId") @NotNull UUID productId) {
        log.debug("getProducts by id {}", productId);
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PutMapping("products/{productId}")
    @Operation(summary = "Edit product", description = "Edits specified product and returns ProductDto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<ProductDto> putProduct(@Parameter(description = "Product ID")
                                                 @PathVariable("productId") @NotNull UUID productId,
                                                 @Parameter(description = "Product DTO")
                                                 @RequestBody @Valid @NotNull ProductUpdateDto productUpdateDto) {
        log.debug("putProduct by id {}", productId);
        return ResponseEntity.ok(productService.putProduct(productId, productUpdateDto));
    }
}
