package ru.din.test.dto.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.din.test.dto.ProductDto;
import ru.din.test.dto.ProductUpdateDto;
import ru.din.test.entity.ProductEntity;

@Mapper(componentModel = "spring",
        uses = ProductMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
    ProductEntity MAPPER = Mappers.getMapper(ProductEntity.class);


    ProductDto toDto(ProductEntity productEntity);

    @Mapping(target = "modifiedAt", expression = "java( java.time.LocalDateTime.now() )")
    ProductEntity toEntity(ProductDto productDto);

    @Mapping(target = "modifiedAt", expression = "java( java.time.LocalDateTime.now() )")
    ProductEntity toEntity(ProductUpdateDto productUpdateDto);

    @Mapping(target = "modifiedAt", expression = "java( java.time.LocalDateTime.now() )")
    ProductEntity update(@MappingTarget ProductEntity productEntity, ProductUpdateDto productUpdateDto);
}
