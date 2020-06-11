package ru.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.entity.Category;
import ru.edu.entity.User;
import ru.edu.exсeptions.entity.EntityIllegalArgumentException;
import ru.edu.exсeptions.entity.EntityNotFoundException;
import ru.edu.jpa.RoleRepository;
import ru.edu.jpa.UserRepository;
import ru.edu.service.UserService;
import ru.edu.service.Utillity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityUserService implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityUserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Object id) {
        Optional<User> user = userRepository.findById(Utillity.parseId(id));
        if (!user.isPresent()) {
            throw new EntityNotFoundException(Category.TYPE_NAME, id);
        }
        return user.get();
    }

    @Override
    public User create(User user) {
        checkUser(user);
        user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public User update(User user) {
        checkUser(user);
        userRepository.save(user);
        return user;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void delete(Object id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new EntityIllegalArgumentException("Объект не может быть null");
        }
        if (user.getName() == null) {
            throw new EntityIllegalArgumentException("Имя не может быть null");
        }
        if (user.getPassword() == null) {
            throw new EntityIllegalArgumentException("Пароль не может быть null");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }
}
