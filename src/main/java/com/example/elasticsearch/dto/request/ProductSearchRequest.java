package com.example.elasticsearch.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSearchRequest {
    private String keyword;
    private List<String> tags;
    private Double priceFrom;
    private Double priceTo;
}