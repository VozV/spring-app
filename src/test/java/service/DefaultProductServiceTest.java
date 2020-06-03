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
import ru.edu.entity.Product;
import ru.edu.exсeptions.entity.EntityAlreadyExistsException;
import ru.edu.exсeptions.entity.EntityIllegalArgumentException;
import ru.edu.exсeptions.entity.EntityNotFoundException;
import ru.edu.service.impl.DefaultCategoryService;
import ru.edu.service.impl.DefaultProductService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class DefaultProductServiceTest {

    @Autowired
    private DefaultProductService defaultProductService;
    @Autowired
    private DefaultCategoryService defaultCategoryService;

    @Test
    public void findAllTest() {
        List<Product> products = defaultProductService.findAll();
        Assert.assertEquals(products.size(), 5);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullException() {
        defaultProductService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNoNumericIdException() {
        defaultProductService.findById("vv");
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFoundException() {
        defaultProductService.findById(10);
    }

    @Test
    public void findByIdSuccess() {
        Product product = defaultProductService.findById(2);
        Assert.assertEquals((int) product.getId(), 2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void addNullProductException() {
        defaultProductService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void addNullIdProductException() {
        defaultProductService.create(new Product());
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void addProductWithNullCategory() {
        Product product = new Product();
        product.setId(2);
        product.setCategory(null);
        defaultProductService.create(product);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void addProductWithNullIdCategory() {
        Product product = new Product();
        product.setId(2);
        product.setCategory(new Category());
        defaultProductService.create(product);
    }

    @Test(expected = EntityNotFoundException.class)
    public void addProductNoEntityCategoryException() {
        Category category = new Category();
        category.setId(10);
        Product product = new Product();
        product.setId(2);
        product.setCategory(category);
        defaultProductService.create(product);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void addProductAlreadyExistsException() {
        Category category = new Category();
        category.setId(1);
        Product product = new Product();
        product.setId(2);
        product.setCategory(category);
        defaultProductService.create(product);
    }

    @Test
    public void addProductSuccess() {
        Category category = new Category();
        category.setId(1);
        Product product = new Product("testName", category);
        defaultProductService.create(product);
    }

    @Test
    public void addCategorySuccess() {
        Category category = new Category();
        category.setId(10);
        category.setName("testName");
        Assert.assertEquals(defaultCategoryService.create(category).getId(), category.getId());
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void removeNullException() {
        defaultProductService.delete(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void removeNoNumericIdException() {
        defaultProductService.delete("vv");
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeNotFoundException() {
        defaultProductService.delete(10);
    }

    @Test
    public void removeSuccess() {
        defaultProductService.create(new Product("delTest", defaultCategoryService.findById(4)));
        defaultProductService.delete(6);
    }


}
