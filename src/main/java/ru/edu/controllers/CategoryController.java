package ru.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.edu.entity.Category;
import ru.edu.service.CrudService;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@Secured("ROLE_USER")
public class CategoryController {

    private final CrudService<Category> categoryService;

    @Autowired
    public CategoryController(CrudService<Category> categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public Category findById(@PathVariable String id) {
        return categoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public Category update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        categoryService.delete(id);
    }
}
