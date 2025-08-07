package com.flashvagas.api.flashvagas_api.application.mapper.product;

import java.time.OffsetDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.api.flashvagas_api.domain.entity.products.Product;
import com.flashvagas.api.flashvagas_api.domain.entity.products.dto.ProductResponse;
import com.flashvagas.api.flashvagas_api.persistence.products.ProductEntity;

@Mapper(componentModel = "spring", imports = {OffsetDateTime.class})
public interface ProductJpaMapper {
    @Mapping(target = "id", expression = "java(new ProductId(entity.getId()))")
    @Mapping(target = "price", expression = "java(new ProductPrices(entity.getDefaultPrice(), entity.getClientPrice()))")
    @Mapping(target = "details", expression = "java(new ProductDetails(entity.getName(), entity.getDescription(), entity.getActive(), entity.getClientDescriptions()))")
    @Mapping(target = "dates", expression = "java(new ProductDates(entity.getCreated(), entity.getUpdated()))")
    Product entityToDomain(ProductEntity entity);

    @Mapping(target = "id", expression = "java(domain.getId().getValue())")
    @Mapping(target = "name", expression = "java(domain.getDetails().getName())")
    @Mapping(target = "description", expression = "java(domain.getDetails().getDescription())")
    @Mapping(target = "active", expression = "java(domain.getDetails().getActive())")
    @Mapping(target = "clientPrice", expression = "java(domain.getPrice().getClientPrice())")
    @Mapping(target = "defaultPrice", expression = "java(domain.getPrice().getDefaultPrice())")
    @Mapping(target = "created", expression = "java(domain.getDates().getCreated())")
    @Mapping(target = "updated", expression = "java(domain.getDates().getUpdated())")
    @Mapping(target = "clientDescriptions", expression = "java(domain.getDetails().getClientDescriptions().getItems())")
    ProductResponse domainToResponse(Product domain);

    List<ProductResponse> domainListToResponseList(List<Product> domainList);
}