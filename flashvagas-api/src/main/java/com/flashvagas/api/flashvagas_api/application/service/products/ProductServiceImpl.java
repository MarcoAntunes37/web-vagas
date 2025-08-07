package com.flashvagas.api.flashvagas_api.application.service.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashvagas.api.flashvagas_api.application.mapper.product.ProductJpaMapper;
import com.flashvagas.api.flashvagas_api.domain.entity.products.Product;
import com.flashvagas.api.flashvagas_api.domain.entity.products.dto.GetAllProductsResponse;
import com.flashvagas.api.flashvagas_api.domain.entity.products.dto.ProductResponse;
import com.flashvagas.api.flashvagas_api.infrastructure.repository.products.ProductRepository;
import com.flashvagas.api.flashvagas_api.persistence.products.ProductEntity;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    private ProductJpaMapper productJpaMapper;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public GetAllProductsResponse getAllProducts() {
        List<ProductEntity> productsEntity = productRepository.findAll();

        List<Product> productsDomain = new ArrayList<>();

        for (ProductEntity productEntity : productsEntity) {
            Product product = productJpaMapper.entityToDomain(productEntity);

            productsDomain.add(product);
        }

        List<ProductResponse> response = productJpaMapper.domainListToResponseList(productsDomain);

        GetAllProductsResponse getAllProductsResponse = new GetAllProductsResponse(response);

        return getAllProductsResponse;
    }

    ;
}
