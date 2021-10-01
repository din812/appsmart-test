package ru.din.test.dto.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.din.test.dto.CustomerDto;
import ru.din.test.dto.CustomerUpdateDto;
import ru.din.test.entity.CustomerEntity;

@Mapper(componentModel = "spring",
        uses = CustomerMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CustomerMapper {
    CustomerEntity MAPPER = Mappers.getMapper(CustomerEntity.class);

    CustomerDto toDto(CustomerEntity entity);

    @Mapping(target = "modifiedAt", expression = "java( java.time.LocalDateTime.now() )")
    CustomerEntity toEntity(CustomerDto dto);

    @Mapping(target = "modifiedAt", expression = "java( java.time.LocalDateTime.now() )")
    CustomerEntity update(@MappingTarget CustomerEntity customerEntity, CustomerUpdateDto customerUpdateDto);
}
