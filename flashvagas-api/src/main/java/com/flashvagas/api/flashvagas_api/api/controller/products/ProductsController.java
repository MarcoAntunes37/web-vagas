package com.flashvagas.api.flashvagas_api.api.controller.products;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashvagas.api.flashvagas_api.application.service.products.ProductService;
import com.flashvagas.api.flashvagas_api.domain.entity.products.dto.GetAllProductsResponse;

import org.springframework.web.bind.annotation.GetMapping;

@RestController("Products Api")
@RequestMapping("api/v1/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public GetAllProductsResponse getAllProducts() {
        return productService.getAllProducts();
    }
}