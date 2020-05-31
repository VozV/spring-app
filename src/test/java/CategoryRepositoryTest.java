import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.edu.entity.Category;
import ru.edu.jpa.CategoryRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void addCategoryTest() {
        Category category = new Category(5,"testCategory");
        categoryRepository.save(category);
        Assert.assertNotNull(categoryRepository.findOne(5));
    }

    @Test
    public void findAllCategoriesTest() {
        List<Category> categories = categoryRepository.findAll();
        Assert.assertEquals(categories.size(), 5);
    }

    @After
    public void delCategoryTest() {
        categoryRepository.delete(5);
        Assert.assertNull(categoryRepository.findOne(5));
    }
}
