package ru.din.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.din.test.entity.ProductEntity;

import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {

    @Query("select c from ProductEntity c where c.isDeleted=false and c.customer.id=:customerId")
    Page<ProductEntity> findAllPageable(UUID customerId, Pageable pageable);

}
