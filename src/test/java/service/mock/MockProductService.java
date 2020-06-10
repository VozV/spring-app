package service.mock;

import ru.edu.entity.Product;
import ru.edu.service.CrudService;

import java.util.ArrayList;
import java.util.List;

public class MockProductService implements CrudService<Product> {
    @Override
    public List<Product> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Product findById(Object id) {
        return new Product();
    }

    @Override
    public Product create(Product product) {
        return product;
    }

    @Override
    public Product update(Product product) {
        return product;
    }

    @Override
    public void delete(Object id) {

    }
}
