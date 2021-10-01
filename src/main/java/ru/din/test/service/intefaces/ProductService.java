package ru.din.test.service.intefaces;

import org.springframework.data.domain.Page;
import ru.din.test.dto.ProductDto;
import ru.din.test.dto.ProductUpdateDto;

import java.util.UUID;

public interface ProductService {

    /**
     * Returns Page of costumer product DTO's, sorted in ascending order
     *
     * @param customerId Customer's UUID
     * @param limit      object per page
     * @param page       page id
     * @return Page<ProductDto>
     */
    Page<ProductDto> getProductsList(UUID customerId, int limit, int page);

    /**
     * Creates product for specified customer, saves it and returns productDto
     *
     * @param customerId       Customer's UUID
     * @param productUpdateDto Product data
     * @return ProductDto
     */
    ProductDto createProduct(UUID customerId, ProductUpdateDto productUpdateDto);

    /**
     * Deletes product from database (completely, use putProduct for setting is_deleted flag)
     *
     * @param productId UUID of product entity
     */
    void deleteProduct(UUID productId);

    /**
     * Find's product entity and returns product's DTO
     *
     * @param productId UUID of product entity
     * @return ProductDto
     */
    ProductDto getProduct(UUID productId);

    /**
     * Updates specified product by his UUID with productDto (UUID and createdAt is ignored by mapper)
     *
     * @param productId        UUID from path variable
     * @param productUpdateDto DTO from request body
     * @return Updated ProductDto
     */
    ProductDto putProduct(UUID productId, ProductUpdateDto productUpdateDto);

}
