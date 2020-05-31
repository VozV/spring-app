package ru.edu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
