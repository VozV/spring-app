package ru.edu.service;

import ru.edu.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Object id);

    Product create(Product Product);

    Product update(Product Product);

    void delete(Object id);
}
