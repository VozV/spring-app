package ru.edu.service;

import ru.edu.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Object id);

    Category create(Category category);

    Category update(Category category);

    void delete(Object id);

}
