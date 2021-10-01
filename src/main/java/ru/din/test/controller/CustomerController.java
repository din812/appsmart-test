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
import ru.din.test.dto.CustomerDto;
import ru.din.test.dto.CustomerUpdateDto;
import ru.din.test.service.intefaces.CustomerService;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.UUID;

@RestController
@RequestMapping(value = "v1/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Customers", description = "Customer related services")
@Slf4j
@CrossOrigin
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(summary = "Returns paginated list of all customers", description = "Returns customer list in basic Page spring object, filters deleted users. Sorted in ascending order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<Page<CustomerDto>> getCustomers(@Parameter(description = "Limit per page")
                                                          @RequestParam(defaultValue = "10")
                                                          @Valid @NotEmpty @Min(1) int limit,
                                                          @Parameter(description = "Page number, default is 1")
                                                          @RequestParam(defaultValue = "1")
                                                          @Valid @NotEmpty @Min(1) int page) {
        log.debug("getCustomers limit {} page {}", limit, page);
        return ResponseEntity.ok(customerService.getCustomersList(limit, page));
    }

    @PostMapping
    @Operation(summary = "Create new customer", description = "Creates new valid customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<CustomerDto> createCustomer(@Parameter(description = "Customer's title", example = "Alan Shepard")
                                                      @RequestParam @Valid @NotBlank @Size(max = 255) String title) {
        log.debug("createCustomer with title {}", title);
        return ResponseEntity.ok(customerService.createCustomer(title));
    }


    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete customer", description = "Completely deletes customer from database by ID, use put/{customerId} in order to change is_deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<Void> deleteCustomer(@Parameter(description = "Customer ID")
                                               @PathVariable("customerId") @NotNull UUID customerId) {
        log.debug("deleteCustomer {}", customerId);
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Returns customer info", description = "Returns CustomerDto with info from CustomerEntity by customer UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<CustomerDto> getCustomer(@Parameter(description = "Customer ID")
                                                   @PathVariable("customerId") @NotNull UUID customerId) {
        log.debug("getCustomers by id {}", customerId);
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Updates specified customer", description = "Updates CustomerEntity by UUID and CustomerDto (UUID and createdAt ignored)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = @Content(schema = @Schema(implementation = Exception.class))), //some custom exception class
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(implementation = Exception.class))) //some custom exception class
    })
    public ResponseEntity<CustomerDto> putCustomer(@Parameter(description = "Customer ID")
                                                   @PathVariable("customerId") @NotNull UUID customerId,
                                                   @Parameter(description = "Customer DTO")
                                                   @RequestBody @Valid @NotNull CustomerUpdateDto customerUpdateDto) {
        log.debug("putCustomer by id {}", customerId);
        return ResponseEntity.ok(customerService.putCustomer(customerId, customerUpdateDto));
    }

}
