/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_admin.controller;

import co.g2academy.healme_admin.model.User;
import co.g2academy.healme_admin.repository.UserRepository;
import co.g2academy.healme_admin.validator.RegexEmailValidator;
import co.g2academy.healme_admin.validator.RegexpasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author personal
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    
    @Autowired
    private UserRepository repository;
    @Autowired
    private RegexEmailValidator emailValidator;
    @Autowired
    private RegexpasswordValidator passwordValidator;
    private PasswordEncoder paswordEncoder = new BCryptPasswordEncoder();
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        User userFromDb = repository.findUserByUsername(user.getUsername());
        if (userFromDb == null && emailValidator.emailPatternMatch(user.getUsername())
                && passwordValidator.passwordPatternMatch(user.getPassword())) {
            user.setPassword(paswordEncoder.encode(user.getPassword()));
            repository.save(user);
        } else {
            return ResponseEntity.badRequest().body("email and password invalid");
        }
        return ResponseEntity.ok().body("OK");
    }    
}
