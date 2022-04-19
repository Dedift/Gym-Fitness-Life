package by.tms.gymprogect.web.controller;

import by.tms.gymprogect.database.dto.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    /**
     * Get user/home page
     */
    @GetMapping("/user/home")
    public String homePage() {
        return "user/home";
    }

    /**
     * Add current user in model and get user/myProfile page
     */
    @GetMapping("/user/myProfile")
    public String profilePage(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        return "user/myProfile";
    }

    /**
     * Get admin/home page
     */
    @GetMapping("/admin/home")
    public String adminPage() {
        return "admin/home";
    }
}
