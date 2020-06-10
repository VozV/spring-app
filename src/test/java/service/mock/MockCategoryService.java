package service.mock;

import ru.edu.entity.Category;
import ru.edu.service.CrudService;

import java.util.ArrayList;
import java.util.List;

public class MockCategoryService implements CrudService<Category> {
    @Override
    public List<Category> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Category findById(Object id) {
        return new Category(Integer.valueOf(String.valueOf(id)), "testCategory");
    }

    @Override
    public Category create(Category category) {
        return category;
    }

    @Override
    public Category update(Category category) {
        return category;
    }

    @Override
    public void delete(Object id) {

    }
}
