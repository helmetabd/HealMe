/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme_admin.repository;

import co.g2academy.healme_admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    public User findUserByUsername(String Username);
}
