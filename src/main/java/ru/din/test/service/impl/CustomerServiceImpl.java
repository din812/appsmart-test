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
import ru.din.test.dto.CustomerDto;
import ru.din.test.dto.CustomerUpdateDto;
import ru.din.test.dto.mapper.CustomerMapper;
import ru.din.test.entity.CustomerEntity;
import ru.din.test.repository.CustomerRepository;
import ru.din.test.service.intefaces.CustomerService;

import java.util.UUID;


@Service
@Slf4j
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerDto> getCustomersList(int limit, int page) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.ASC, "title"));
        return customerRepository.findAllPageable(pageable).map(customerMapper::toDto);
    }

    @Transactional
    @Override
    public CustomerDto createCustomer(String title) {
        return customerMapper.toDto(
                customerRepository.save(CustomerEntity.builder().title(title).build())
        );
    }

    @Transactional
    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerDto getCustomer(UUID customerId) {
        return customerMapper.toDto(
                customerRepository.findByIdAndIsDeletedFalse(customerId)
                        .orElseThrow(() -> new NotFoundException(String.format("[getCustomer] couldn't find customer by id %s", customerId))) //some kind of custom error handling with logger
        );
    }

    @Transactional
    @Override
    public CustomerDto putCustomer(UUID customerId, CustomerUpdateDto customerUpdateDto) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(String.format("[putCustomer] couldn't find customer by id %s", customerId)));

        return customerMapper.toDto(
                customerMapper.update(customerEntity, customerUpdateDto)
        );
    }
}
