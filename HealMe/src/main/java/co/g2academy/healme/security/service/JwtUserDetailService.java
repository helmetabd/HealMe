/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.security.service;

import co.g2academy.healme.model.Patient;
import co.g2academy.healme.repository.PatientRepository;
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
    private PatientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = repository.findPatientByUsername(username);
        if(patient != null){
            org.springframework.security.core.userdetails.User userdetails = new org.springframework.security.core.userdetails.User(username, patient.getPassword(), new ArrayList<>());
        return  userdetails;
        }
        throw new UsernameNotFoundException("User not foound");
    }
}
