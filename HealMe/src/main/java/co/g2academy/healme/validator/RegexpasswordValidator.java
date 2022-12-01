/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.validator;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 *
 * @author personal
 */
@Component
public class RegexpasswordValidator {
    
    private String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    
    public boolean passwordPatternMatch(String password){
        return Pattern.matches(regex, password);
    }
}
