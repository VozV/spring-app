package ru.edu.service.impl;

import org.springframework.stereotype.Service;
import ru.edu.entity.Category;
import ru.edu.entity.Product;
import ru.edu.exсeptions.entity.EntityAlreadyExistsException;
import ru.edu.exсeptions.entity.EntityIllegalArgumentException;
import ru.edu.exсeptions.entity.EntityNotFoundException;
import ru.edu.jpa.CategoryRepository;
import ru.edu.jpa.ProductRepository;
import ru.edu.service.ProductService;

import java.util.List;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public DefaultProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Object id) {
        if (id == null) {
            throw new EntityIllegalArgumentException("Ключ объекта не может быть null");
        }
        Integer parsedId;
        try {
            parsedId = Integer.valueOf(String.valueOf(id));
        } catch (NumberFormatException e) {
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор к " +
                    "нужному типу: %s", e));
        }
        Product product = productRepository.findOne(parsedId);

        if (product == null) {
            throw new EntityNotFoundException(Category.TYPE_NAME, parsedId);
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        if (product == null) {
            throw new EntityIllegalArgumentException("Объект не может быть null");
        }
        if (product.getCategory() == null) {
            throw new EntityIllegalArgumentException("Категория не может быть null");
        }
        if (product.getCategory().getId() == null) {
            throw new EntityIllegalArgumentException("Id категории не может быть null");
        }
        if (categoryRepository.findOne(product.getCategory().getId()) == null) {
            throw new EntityNotFoundException(Category.TYPE_NAME, product.getCategory().getId());
        }

        if (product.getId() != null && productRepository.findOne(product.getId()) == null) {
            throw new EntityAlreadyExistsException(Product.TYPE_NAME, product.getId());
        }
        return productRepository.save(product);
    }

    public Product create(Product product) {
        if (product == null) {
            throw new EntityIllegalArgumentException("Объект не может быть null");
        }
        if (product.getCategory() == null) {
            throw new EntityIllegalArgumentException("Категория не может быть null");
        }
        if (product.getCategory().getId() == null) {
            throw new EntityIllegalArgumentException("Id категории не может быть null");
        }
        if (categoryRepository.findOne(product.getCategory().getId()) == null) {
            throw new EntityNotFoundException(Category.TYPE_NAME, product.getCategory().getId());
        }

        if (product.getId() != null && productRepository.findOne(product.getId()) != null) {
            throw new EntityAlreadyExistsException(Product.TYPE_NAME, product.getId());
        }
        return productRepository.save(product);
    }

    public void delete(Object id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
