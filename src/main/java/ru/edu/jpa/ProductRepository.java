package ru.edu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.edu.entity.ProductJpa;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductJpa, Long> {

    @Query (value = "select count (*) from product where category_id = :categoryId", nativeQuery = true)
    Integer getCountProductForCategory(@Param("categoryId") long categoryId);

    List<ProductJpa> findByName(String name);
}
