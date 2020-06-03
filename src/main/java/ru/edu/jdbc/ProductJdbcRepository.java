package ru.edu.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.edu.entity.ProductJdbc;

import java.util.List;

@Repository
public class ProductJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from product", Integer.class);
    }

    public List<ProductJdbc> getProducts() {
        return jdbcTemplate.query("select product.id, product.category, product.name from product", (resultSet, i) ->
                    new ProductJdbc(
                            resultSet.getLong(1),
                            resultSet.getLong(2),
                            resultSet.getString(3)
                    ));
    }

    public List<ProductJdbc> getProducts(String categoryName) {
        return jdbcTemplate.query("select p.id, p.category, p.name from product p inner join category c " +
                "on p.category = c.id where c.name = ?", new Object[] { categoryName }, (resultSet, i) ->
                new ProductJdbc(
                        resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getString(3)
                ));
    }
}
