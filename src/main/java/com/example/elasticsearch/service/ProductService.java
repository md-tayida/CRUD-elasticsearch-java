package com.example.elasticsearch.service;

import com.example.elasticsearch.dto.request.ProductCreateRequest;
import com.example.elasticsearch.dto.request.ProductSearchRequest;
import com.example.elasticsearch.dto.response.ProductResponse;
import com.example.elasticsearch.mapper.ProductMapper;
import com.example.elasticsearch.model.Product;
import com.example.elasticsearch.repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
//import org.springframework.util.ObjectUtils;
//import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    //@Autowired
    private final ElasticsearchOperations operations;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
public ProductService(ElasticsearchOperations operations, ProductRepository productRepository, ProductMapper productMapper){
    this.operations = operations;
    this.productRepository = productRepository;
    this.productMapper = productMapper;
}
    public ProductResponse createProduct(ProductCreateRequest request) {
    Product product = productRepository.save(Product.builder()
            .id(UUID.randomUUID().toString())
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .tags(request.getTags())
            .build());
        return productMapper.toProductResponse(product);
    }

    public ProductResponse getProductById(String id){
Product product = productRepository.findById(id).orElse(null);
return  productMapper.toProductResponse(product);

    }

    public List<ProductResponse> searchProducts(ProductSearchRequest request, int page, int size) {
        Criteria criteria = new Criteria();

        // ค้นหา keyword ใน name หรือ description
        if (StringUtils.isNotBlank(request.getKeyword())) {
            Criteria nameContains = new Criteria("name")
                    .contains(request.getKeyword())
                    .boost(2.0f); // ให้ weight กับ name มากกว่า
            Criteria descContains = new Criteria("description")
                    .contains(request.getKeyword());
            criteria = criteria.or(nameContains).or(descContains);
        }

        // filter ด้วย tags
        if (!CollectionUtils.isEmpty(request.getTags())) {
            for (String tag : request.getTags()) {
                criteria = criteria.and(new Criteria("tags").is(tag));
            }
        }

        // filter ด้วย price from
        if (request.getPriceFrom() != null) {
            criteria = criteria.and(new Criteria("price").greaterThanEqual(request.getPriceFrom()));
        }

        if (request.getPriceTo() != null) {
            criteria = criteria.and(new Criteria("price").lessThanEqual(request.getPriceTo()));
        }


        // Query Elasticsearch
        CriteriaQuery query = new CriteriaQuery(criteria, PageRequest.of(page, size));

        SearchHits<Product> searchHits = operations.search(query, Product.class);

        List<Product> products = searchHits
                .stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());

        return ProductMapper.toProductResponseList(products);
    }

    public ProductResponse updateProduct(String id, ProductCreateRequest request){
    Product product = productRepository.findById(id).orElse(null);
    product.setName(request.getName());
    product.setDescription(request.getDescription());
    product.setPrice(request.getPrice());
    product.setTags(request.getTags());

    Product updatedProduct = productRepository.save(product);
    return ProductMapper.toProductResponse(updatedProduct);
    }

    public void deleteProductById(String id){
    productRepository.deleteById(id);

    }
    // ใน ProductService
    public List<ProductResponse> getAllProducts() {
        List<Product> products = StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return ProductMapper.toProductResponseList(products); // เรียกใช้เมธอด static ของ Mapper
    }

}



