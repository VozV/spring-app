package ru.edu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.edu.entity.Category;
import ru.edu.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query (value = "select count (*) from product where category = :categoryId", nativeQuery = true)
    Integer getCountProductForCategory(@Param("categoryId") long categoryId);

    List<Product> findByName(String name);

    List<Product> findByCategory(Category category);
}
