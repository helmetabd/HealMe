/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_admin.security.service;

import co.g2academy.healme_admin.model.User;
import co.g2academy.healme_admin.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author personal
 */
@Component
public class JwtUserDetailService implements UserDetailsService{
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username);
        if(user != null){
            org.springframework.security.core.userdetails.User userdetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
        return  userdetails;
        }
        throw new UsernameNotFoundException("User not foound");
    }
}
