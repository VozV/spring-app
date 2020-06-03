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
import ru.edu.service.impl.DefaultCategoryService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class DefaultCategoryServiceTest {

    @Autowired
    private DefaultCategoryService defaultCategoryService;

    @Test
    public void findAllTest() {
        List<Category> categories = defaultCategoryService.findAll();
        Assert.assertEquals(categories.size(), 4);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullCategoryException() {
        defaultCategoryService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdCategoryException() {
        defaultCategoryService.create(new Category());
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createAlreadyExistsCategoryException() {
        Category category = new Category();
        category.setId(1);
        defaultCategoryService.create(category);
    }

    @Test
    public void createCategorySuccess() {
        Category category = new Category();
        category.setId(10);
        category.setName("testName");
        Assert.assertEquals(defaultCategoryService.create(category).getId(), category.getId());
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullException() {
        defaultCategoryService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNoNumericIdException() {
        defaultCategoryService.findById("vv");
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFoundException() {
        defaultCategoryService.findById(10);
    }

    @Test
    public void findByIdSuccess() {
        Category category = defaultCategoryService.findById(2);
        Assert.assertEquals((int) category.getId(), 2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void deleteNullException() {
        defaultCategoryService.delete(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void deleteNoNumericIdException() {
        defaultCategoryService.delete("vv");
    }

    @Test(expected = EntityHasDetailsException.class)
    public void deleteNotFoundException() {
        defaultCategoryService.delete(3);
    }

    @Test
    public void deleteSuccess() {
        defaultCategoryService.delete(4);
    }
}
