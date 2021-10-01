package ru.din.test.service.intefaces;

import org.springframework.data.domain.Page;
import ru.din.test.dto.CustomerDto;
import ru.din.test.dto.CustomerUpdateDto;

import java.util.UUID;

public interface CustomerService {

    /**
     * Returns Page of customer DTO's, sorted in ascending order
     *
     * @param limit object per page
     * @param page  page id
     * @return Page<CustomerDto>
     */
    Page<CustomerDto> getCustomersList(int limit, int page);

    /**
     * Creates customer, saves it and returns customerDto
     *
     * @param title customer's title
     * @return CustomerDto
     */
    CustomerDto createCustomer(String title);

    /**
     * Deletes customer from database (completely, use putCustomer for setting is_deleted flag)
     *
     * @param customerId UUID of customer entity
     */
    void deleteCustomer(UUID customerId);

    /**
     * Find's customer entity and returns customer's DTO
     *
     * @param customerId UUID of customer entity
     * @return CustomerDto
     */
    CustomerDto getCustomer(UUID customerId);

    /**
     * Updates specified customer by his UUID with customerDto (UUID and createdAt is ignored by mapper)
     *
     * @param customerId        UUID from path variable
     * @param customerUpdateDto DTO from request body
     * @return Updated CustomerDto
     */
    CustomerDto putCustomer(UUID customerId, CustomerUpdateDto customerUpdateDto);
}
