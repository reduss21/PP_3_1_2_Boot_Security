package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);

    List<User> allUsers();

    boolean saveUser(User user/*, Set<Role> roles*/);

    boolean deleteUserById(Long id);

    void update(User user);
}
