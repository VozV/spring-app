package ru.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.edu.entity.Category;
import ru.edu.entity.User;
import ru.edu.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userList")
    public List<User> userList() {
        return userService.findAll();
    }

    @GetMapping("/user/{userId}")
    public User findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @PutMapping
    @GetMapping("/user")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }
}
