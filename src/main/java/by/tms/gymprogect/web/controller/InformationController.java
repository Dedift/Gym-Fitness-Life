package by.tms.gymprogect.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformationController {

    /**
     * Get page user/information
     */
    @GetMapping("/user/information")
    public String informationPage(){
        return "user/information";
    }

    /**
     * Get page admin/information
     */
    @GetMapping("/admin/information")
    public String adminInformationPage(){
        return "admin/information";
    }
}
