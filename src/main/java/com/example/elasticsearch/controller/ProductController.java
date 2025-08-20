package com.example.elasticsearch.controller;

import com.example.elasticsearch.dto.request.ProductCreateRequest;
import com.example.elasticsearch.dto.request.ProductSearchRequest;
import com.example.elasticsearch.dto.response.ProductResponse;
import com.example.elasticsearch.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint สำหรับการสร้าง Product (POST /api/v1/products/create)
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
        ProductResponse product = productService.createProduct(request);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Endpoint สำหรับการดึง Product รายการเดียวด้วย ID (GET /api/v1/products/get/{id})
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }


    // Endpoint สำหรับการค้นหา Product (GET /api/v1/products/search)
    @PostMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestBody ProductSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ProductResponse> products = productService.searchProducts(request, page, size);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @RequestBody ProductCreateRequest request){
        ProductResponse product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/get")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);

    }
}