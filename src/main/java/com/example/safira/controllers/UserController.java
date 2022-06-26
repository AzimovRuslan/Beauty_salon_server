package com.example.safira.controllers;

import com.example.safira.models.Certificate;
import com.example.safira.models.User;
import com.example.safira.services.UserService;
import com.example.safira.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAll() {

        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{value}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<User> get(@PathVariable("value") String value) {
        User user = new User();

        if (value != null) {
            if (value.matches("[+]?\\d+")) {
                user = Util.getRecordFromTable(Long.parseLong(value), userService);
            } else {
                user = userService.findAll()
                        .stream()
                        .filter(u -> u.getUsername().equals(value))
                        .findFirst()
                        .orElse(null);
            }
        }

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> delete(@PathVariable long id) {
        User deleteUser = Util.getRecordFromTable(id, userService);
        userService.delete(deleteUser);

        return ResponseEntity.ok().body(deleteUser);
    }
}
