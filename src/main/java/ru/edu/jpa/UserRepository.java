package ru.edu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
}
