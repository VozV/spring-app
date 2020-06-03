package ru.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.entity.Category;
import ru.edu.exсeptions.entity.EntityAlreadyExistsException;
import ru.edu.exсeptions.entity.EntityHasDetailsException;
import ru.edu.exсeptions.entity.EntityIllegalArgumentException;
import ru.edu.exсeptions.entity.EntityNotFoundException;
import ru.edu.jpa.CategoryRepository;
import ru.edu.jpa.ProductRepository;
import ru.edu.service.CategoryService;

import java.util.List;

@Service
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DefaultCategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Object id) {
        if (id == null) {
            throw new EntityIllegalArgumentException("Ключ объекта не может быть null");
        }
        Integer parsedId;
        try {
            //костыль, т.к. ((String) id) выкидывает java.lang.ClassCastException
            parsedId = Integer.valueOf(String.valueOf(id));
        } catch (NumberFormatException e) {
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор к " +
                    "нужному типу: %s", e));
        }
        Category category = categoryRepository.findOne(parsedId);
        if (category == null) {
            throw new EntityNotFoundException(Category.TYPE_NAME, parsedId);
        }
        return category;
    }

    @Override
    public Category create(Category category) {
        if (category == null) {
            throw new EntityIllegalArgumentException("Объект не может быть null");
        }
        if (category.getId() == null) {
            throw new EntityIllegalArgumentException("Идентефикатор объекта не может быть null");
        }
        if (categoryRepository.findOne(category.getId()) != null) {
            throw new EntityAlreadyExistsException(Category.TYPE_NAME, category.getId());
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        if (category == null) {
            throw new EntityIllegalArgumentException("Объект не может быть null");
        }
        if (category.getId() == null) {
            throw new EntityIllegalArgumentException("Идентефикатор объекта не может быть null");
        }
        if (categoryRepository.findOne(category.getId()) == null) {
            throw new EntityNotFoundException(Category.TYPE_NAME, category.getId());
        }
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Object id) {
        Category category = findById(id);
        if (productRepository.findByCategory(category).size() > 0) {
            throw new EntityHasDetailsException(Category.TYPE_NAME, category.getId());
        }
        categoryRepository.delete(category);
    }

}
