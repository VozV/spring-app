package service;

import config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.edu.entity.Category;
import ru.edu.exсeptions.entity.EntityAlreadyExistsException;
import ru.edu.exсeptions.entity.EntityHasDetailsException;
import ru.edu.exсeptions.entity.EntityIllegalArgumentException;
import ru.edu.exсeptions.entity.EntityNotFoundException;
import ru.edu.service.CategoryService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findAllTest() {
        List<Category> categories = categoryService.findAll();
        Assert.assertEquals(categories.size(), 4);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullCategoryException() {
        categoryService.addCategory(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdCategoryException() {
        categoryService.addCategory(new Category());
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createAlreadyExistsCategoryException() {
        Category category = new Category();
        category.setId(1);
        categoryService.addCategory(category);
    }

    @Test
    public void createCategorySuccess() {
        Category category = new Category();
        category.setId(10);
        category.setName("testName");
        Assert.assertEquals(categoryService.addCategory(category).getId(), category.getId());
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullException() {
        categoryService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNoNumericIdException() {
        categoryService.findById("vv");
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFoundException() {
        categoryService.findById(10);
    }

    @Test
    public void findByIdSuccess() {
        Category category = categoryService.findById(2);
        Assert.assertEquals((int) category.getId(), 2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void removeCategoryNullException() {
        categoryService.removeCategory(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void removeCategoryNoNumericIdException() {
        categoryService.removeCategory("vv");
    }

    @Test(expected = EntityHasDetailsException.class)
    public void removeCategoryNotFoundException() {
        categoryService.removeCategory(3);
    }

    @Test
    public void removeCategorySuccess() {
        categoryService.removeCategory(4);
    }
}
