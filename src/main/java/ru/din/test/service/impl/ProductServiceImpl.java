package ru.din.test.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.din.test.dto.ProductDto;
import ru.din.test.dto.ProductUpdateDto;
import ru.din.test.dto.mapper.ProductMapper;
import ru.din.test.entity.CustomerEntity;
import ru.din.test.entity.ProductEntity;
import ru.din.test.repository.CustomerRepository;
import ru.din.test.repository.ProductRepository;
import ru.din.test.service.intefaces.ProductService;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDto> getProductsList(UUID customerId, int limit, int page) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.ASC, "title"));
        return productRepository.findAllPageable(customerId, pageable).map(productMapper::toDto);
    }

    @Transactional
    @Override
    public ProductDto createProduct(UUID customerId, ProductUpdateDto productUpdateDto) {
        Optional<CustomerEntity> byIdAndIsDeletedFalse = customerRepository.findByIdAndIsDeletedFalse(customerId);

        if (byIdAndIsDeletedFalse.isEmpty()) {
            throw new NotFoundException(String.format("[createProduct] couldn't find customer by id %s", customerId));
        }

        ProductEntity productEntity = productMapper.toEntity(productUpdateDto);
        productEntity.setCustomer(byIdAndIsDeletedFalse.get());

        return productMapper.toDto(productRepository.save(productEntity));
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDto getProduct(UUID productId) {
        return productMapper.toDto(
                productRepository.findById(productId)
                        .orElseThrow(() -> new NotFoundException(String.format("[getProduct] couldn't find product by id %s", productId))) //some kind of custom error handling with logger
        );
    }

    @Transactional
    @Override
    public ProductDto putProduct(UUID productId, ProductUpdateDto productUpdateDto) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("[getProduct] couldn't find customer by id %s", productId)));

        return productMapper.toDto(
                productMapper.update(productEntity, productUpdateDto)
        );
    }

}
