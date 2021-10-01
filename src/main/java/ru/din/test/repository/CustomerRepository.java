package ru.din.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.din.test.entity.CustomerEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, UUID> {

    @Query("select c from CustomerEntity c where c.isDeleted=false")
    Page<CustomerEntity> findAllPageable(Pageable pageable);

    Optional<CustomerEntity> findByIdAndIsDeletedFalse(UUID customerId); //another way to implement query
}
