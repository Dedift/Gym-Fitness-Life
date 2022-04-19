package by.tms.gymprogect.web.controller;

import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.dto.UserDTO;
import by.tms.gymprogect.database.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Add userDTO in model, and get register page
     */
    @GetMapping("/register")
    public String registerForm(UserDTO userDTO, Model model){
        model.addAttribute("user", userDTO).addAttribute("genders", Gender.values());
        return "register";
    }

    /**
     * Accept userDTO from register page, save him and redirect to user/home
     */
    @PostMapping("/register")
    public String registerUser(UserDTO userDTO){
        userDTO.setRole(Role.USER);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.save(userDTO);
        return "redirect: /user/home";
    }

    /**
     * Add userDTO in model, and get admin register page
     */
    @GetMapping("/admin/register")
    public String adminRegisterForm(UserDTO userDTO, Model model){
        model.addAttribute("user", userDTO).addAttribute("genders", Gender.values());
        return "admin/register";
    }

    /**
     * Accept userDTO from admin register page, save him, and redirect to admin/home
     */
    @PostMapping("/admin/register")
    public String registerAdmin(UserDTO userDTO){
        userDTO.setRole(Role.ADMIN);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.save(userDTO);
        return "redirect: /admin/home";
    }
}
