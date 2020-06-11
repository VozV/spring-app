package ru.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.edu.entity.Category;
import ru.edu.exсeptions.entity.EntityAlreadyExistsException;
import ru.edu.exсeptions.entity.EntityHasDetailsException;
import ru.edu.exсeptions.entity.EntityIllegalArgumentException;
import ru.edu.exсeptions.entity.EntityNotFoundException;
import ru.edu.jpa.CategoryRepository;
import ru.edu.jpa.ProductRepository;
import ru.edu.service.CrudService;
import ru.edu.service.Utillity;

import java.util.List;
import java.util.Optional;

@Service
@Secured("ROLE_USER")
public class CategoryService implements CrudService<Category> {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Object id) {
        Optional<Category> category = categoryRepository.findById(Utillity.parseId(id));
        if (!category.isPresent()) {
            throw new EntityNotFoundException(Category.TYPE_NAME, id);
        }
        return category.get();
    }

    @Override
    @Secured("ROLE_ADMIN")
    public Category create(Category category) {
        checkCategory(category);
        if (categoryRepository.findById(category.getId()).isPresent()) {
            throw new EntityAlreadyExistsException(Category.TYPE_NAME, category.getId());
        }
        return categoryRepository.save(category);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public Category update(Category category) {
        checkCategory(category);
        if (!categoryRepository.findById(category.getId()).isPresent()) {
            throw new EntityNotFoundException(Category.TYPE_NAME, category.getId());
        }
        return categoryRepository.save(category);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void delete(Object id) {
        Category category = findById(id);
        if (productRepository.findByCategory(category).size() > 0) {
            throw new EntityHasDetailsException(Category.TYPE_NAME, category.getId());
        }
        categoryRepository.delete(category);
    }

    private void checkCategory(Category category) {
        if (category == null) {
            throw new EntityIllegalArgumentException("Объект не может быть null");
        }
        if (category.getId() == null) {
            throw new EntityIllegalArgumentException("Идентефикатор объекта не может быть null");
        }
    }

}
