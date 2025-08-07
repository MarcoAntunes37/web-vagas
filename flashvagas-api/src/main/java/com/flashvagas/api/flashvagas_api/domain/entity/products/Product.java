package com.flashvagas.api.flashvagas_api.domain.entity.products;

import com.flashvagas.api.flashvagas_api.domain.value_object.ProductDates;
import com.flashvagas.api.flashvagas_api.domain.value_object.ProductDetails;
import com.flashvagas.api.flashvagas_api.domain.value_object.ProductId;
import com.flashvagas.api.flashvagas_api.domain.value_object.ProductPrices;

public class Product {
    private ProductId id;
    private ProductPrices price;
    private ProductDetails details;
    private ProductDates dates;

    public Product(ProductId id, ProductPrices price, ProductDetails details, ProductDates dates) {
        this.id = id;
        this.price = price;
        this.details = details;
        this.dates = dates;
    }

    public ProductId getId() {
        return id;
    }

    public ProductPrices getPrice() {
        return price;
    }

    public ProductDetails getDetails() {
        return details;
    }

    public ProductDates getDates() {
        return dates;
    }
}