package ru.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.edu.entity.ProductJdbc;
import ru.edu.entity.Product;
import ru.edu.jdbc.ProductJdbcRepository;
import ru.edu.entity.Category;
import ru.edu.jpa.CategoryRepository;
import ru.edu.jpa.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TestController {

    private final ProductJdbcRepository productJdbc;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public TestController(ProductJdbcRepository productJdbc, CategoryRepository productRepository, ProductJdbcRepository getProductJdbc, CategoryRepository categoryRepository, CategoryRepository categoryRepository1, ProductRepository productRepository1) {
        this.productJdbc = productJdbc;
        this.categoryRepository = categoryRepository1;
        this.productRepository = productRepository1;
    }

    @RequestMapping("/category/list")
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @GetMapping("/product/count")
    public Integer getProductCount() {
        return productJdbc.count();
    }

    @GetMapping("/product/list")
    public List<ProductJdbc> getProductList() {
        return productJdbc.getProducts();
    }

    @PostMapping("/product/list")
    public List<ProductJdbc> getProductList(@RequestParam String category) {
        return productJdbc.getProducts(category);
    }

    @GetMapping("/product/list/jpa")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/product/add/jpa")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PostMapping("/product/category/count")
    public long getCountProductForCategory(@RequestParam long categoryId) {
        return productRepository.getCountProductForCategory(categoryId);
    }

    @GetMapping("/product/get")
    public List<Product> getProductByName(@RequestParam String productName) {
        return productRepository.findByName(productName);
    }
}
