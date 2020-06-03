package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.edu.controllers.CategoryController;
import ru.edu.entity.Category;
import service.mock.MockCategoryService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CategoryController.class, MockCategoryService.class})
public class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    private MockMvc mockMvc;

    private final static String URL = "http://localhost:8080/api/v1/category";

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(categoryController).build();
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void createTest() throws Exception {
        Category category = new Category(2, "testCategory");
        String requestJson = objectMapper.writeValueAsString(category);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findByIdTest() throws Exception {
        mockMvc.perform(get(URL + "/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception {
        Category category = new Category(2, "testCategory");
        String requestJson = objectMapper.writeValueAsString(category);
        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(URL + "/2"))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete(URL))
                .andExpect(status().isMethodNotAllowed());
    }
}
