package ru.edu.service.impl;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.edu.entity.Category;
import ru.edu.entity.Product;
import ru.edu.exсeptions.entity.EntityAlreadyExistsException;
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
public class ProductService implements CrudService<Product> {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Object id) {
        Optional<Product> product = productRepository.findById(Utillity.parseId(id));
        if (!product.isPresent()) {
            throw new EntityNotFoundException(Category.TYPE_NAME, id);
        }
        return product.get();
    }

    @Override
    @Secured("ROLE_ADMIN")
    public Product update(Product product) {
        checkProduct(product);
        if (product.getId() != null && !productRepository.findById(product.getId()).isPresent()) {
            throw new EntityAlreadyExistsException(Product.TYPE_NAME, product.getId());
        }
        return productRepository.save(product);
    }

    @Secured("ROLE_ADMIN")
    public Product create(Product product) {
        checkProduct(product);
        if (product.getId() != null && productRepository.findById(product.getId()).isPresent()) {
            throw new EntityAlreadyExistsException(Product.TYPE_NAME, product.getId());
        }
        return productRepository.save(product);
    }

    @Secured("ROLE_ADMIN")
    public void delete(Object id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    private void checkProduct(Product product) {
        if (product == null) {
            throw new EntityIllegalArgumentException("Объект не может быть null");
        }
        if (product.getCategory() == null) {
            throw new EntityIllegalArgumentException("Категория не может быть null");
        }
        if (product.getCategory().getId() == null) {
            throw new EntityIllegalArgumentException("Id категории не может быть null");
        }
        if (!categoryRepository.findById(product.getCategory().getId()).isPresent()) {
            throw new EntityNotFoundException(Category.TYPE_NAME, product.getCategory().getId());
        }
    }
}
