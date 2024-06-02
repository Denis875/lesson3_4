package ru.kata.spring.boot_security.demo.initializer;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Initializer {
    private UserService userService;
    private RoleService roleService;

    public Initializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role(1L, "ROLE_USER");
        Role role2 = new Role(2L, "ROLE_ADMIN");

        roleService.add(role1);
        roleService.add(role2);

        Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleUser = new HashSet<>();
        roleUser.add(role1);
        roleAdmin.add(role2);
        User user = new User("user", "user", (byte) 14, "user");
        User admin = new User("admin", "admin", (byte) 12, "admin");
        admin.setRoles(roleAdmin);
        admin.setPassword("admin");
        user.setRoles(roleUser);
        user.setPassword("user");
        userService.add(admin);
        userService.add(user);
    }
}