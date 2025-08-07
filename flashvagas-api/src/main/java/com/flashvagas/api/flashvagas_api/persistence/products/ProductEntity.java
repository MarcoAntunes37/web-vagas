package com.flashvagas.api.flashvagas_api.persistence.products;

import java.time.OffsetDateTime;

import com.flashvagas.api.flashvagas_api.domain.value_object.ClientDescriptions;
import com.flashvagas.api.flashvagas_api.persistence.products.converters.ClientDescriptionsConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;
    private Boolean active;
    private String defaultPrice;
    private Double clientPrice;
    private String name;
    private String description;
    @Convert(converter = ClientDescriptionsConverter.class)
    private ClientDescriptions clientDescriptions;
    private OffsetDateTime created;
    private OffsetDateTime updated;
}