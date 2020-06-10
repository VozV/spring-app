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
import ru.edu.service.impl.CategoryService;
import ru.edu.service.impl.ProductService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Test
    public void findAllTest() {
        List<Product> products = productService.findAll();
        Assert.assertEquals(products.size(), 5);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullException() {
        productService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNoNumericIdException() {
        productService.findById("vv");
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFoundException() {
        productService.findById(10);
    }

    @Test
    public void findByIdSuccess() {
        Product product = productService.findById(2);
        Assert.assertEquals((int) product.getId(), 2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void addNullProductException() {
        productService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void addNullIdProductException() {
        productService.create(new Product());
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void addProductWithNullCategory() {
        Product product = new Product();
        product.setId(2);
        product.setCategory(null);
        productService.create(product);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void addProductWithNullIdCategory() {
        Product product = new Product();
        product.setId(2);
        product.setCategory(new Category());
        productService.create(product);
    }

    @Test(expected = EntityNotFoundException.class)
    public void addProductNoEntityCategoryException() {
        Category category = new Category();
        category.setId(10);
        Product product = new Product();
        product.setId(2);
        product.setCategory(category);
        productService.create(product);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void addProductAlreadyExistsException() {
        Category category = new Category();
        category.setId(1);
        Product product = new Product();
        product.setId(2);
        product.setCategory(category);
        productService.create(product);
    }

    @Test
    public void addProductSuccess() {
        Category category = new Category();
        category.setId(1);
        Product product = new Product("testName", category);
        productService.create(product);
    }

    @Test
    public void addCategorySuccess() {
        Category category = new Category();
        category.setId(10);
        category.setName("testName");
        Assert.assertEquals(categoryService.create(category).getId(), category.getId());
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void removeNullException() {
        productService.delete(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void removeNoNumericIdException() {
        productService.delete("vv");
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeNotFoundException() {
        productService.delete(10);
    }

    @Test
    public void removeSuccess() {
        productService.create(new Product("delTest", categoryService.findById(4)));
        productService.delete(6);
    }


}
