/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_doctor.security.service;

import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.repository.DoctorRepository;
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
    private DoctorRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = repository.findDoctorByUsername(username);
        if(doctor != null){
            org.springframework.security.core.userdetails.User userdetails = new org.springframework.security.core.userdetails.User(username, doctor.getPassword(), new ArrayList<>());
        return  userdetails;
        }
        throw new UsernameNotFoundException("User not foound");
    }
}
