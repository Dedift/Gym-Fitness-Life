package by.tms.gymprogect.web.controller;

import by.tms.gymprogect.database.dto.PersonalTrainerDTO;
import by.tms.gymprogect.database.service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class TeamController {

    @Autowired
    PersonalTrainerService personalTrainerService;

    /**
     * Add in model all personal trainers from database and get user/team page
     */
    @GetMapping("/user/team")
    public String teamPage(Model model) {
        model.addAttribute("allTrainers", personalTrainerService.findAll());
        return "user/team";
    }

    /**
     * Add in model all personal trainers from database and get admin/team page
     */
    @GetMapping("/admin/team")
    public String adminTeam(Model model) {
        model.addAttribute("allTrainers", personalTrainerService.findAll());
        return "admin/team";
    }

    /**
     * Add personal trainer in model and get admin/newTrainer page
     */
    @GetMapping("/admin/newTrainer")
    public String newTrainerPage(PersonalTrainerDTO personalTrainerDTO, Model model) {
        model.addAttribute("trainer", personalTrainerDTO);
        return "admin/newTrainer";
    }

    /**
     * Accept personal trainer DTO from newTrainer page, save him, and redirect to admin/home
     */
    @PostMapping("/admin/newTrainer")
    public String CreateNewTrainer(PersonalTrainerDTO personalTrainerDTO) {
        if (Objects.nonNull(personalTrainerDTO)) {
            personalTrainerService.save(personalTrainerDTO);
        }
        return "redirect: /admin/home";
    }
}
