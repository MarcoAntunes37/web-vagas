package com.flashvagas.api.flashvagas_api.infrastructure.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashvagas.api.flashvagas_api.persistence.products.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

}
