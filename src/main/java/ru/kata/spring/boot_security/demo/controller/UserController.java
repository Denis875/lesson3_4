package ru.kata.spring.boot_security.demo.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    private UserService us;
    @Autowired
    private RoleService rs;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    /**
     * RestController for User page about user information
     **/
    @GetMapping("/user")
    public ResponseEntity<User> showUserInfo() {
        return new ResponseEntity<>(us.getCurrentUser(), HttpStatus.OK);
    }


    /**
     * RestControllers for Admin with CRUD operation and information about all users
     **/
    @GetMapping(value = "/admin")
    public ResponseEntity<List<User>> startPageForAdmin() {
        List<User> userList = new ArrayList<>();
        for (User user : us.getAll()) {
            userList.add(user);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<User> showUser(@RequestParam Long id) {
        return new ResponseEntity<>(us.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/admin/saveUser")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        us.add(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/deleteUser")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam Long id) {
        us.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/updateUser")
    public ResponseEntity<HttpStatus> updateUserInfo(@RequestBody @NotNull User user, @RequestParam Long id) {
        if (user.getRoles() == null || user.getRoles().size() == 0) {
            user.setRoles(us.getUserById(id).getRoles());
        }
        user.setId(id);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        us.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
