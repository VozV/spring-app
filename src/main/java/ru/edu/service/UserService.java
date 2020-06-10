package ru.edu.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.edu.entity.User;

public interface UserService extends UserDetailsService, CrudService<User> {
}
