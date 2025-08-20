package com.example.elasticsearch.mapper;

import com.example.elasticsearch.dto.response.ProductResponse;
import com.example.elasticsearch.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ProductMapper {
    public static ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .tags(product.getTags())
                .build();
    }

    public static List<ProductResponse> toProductResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toProductResponse)
                .collect(Collectors.toList());
    }


}
