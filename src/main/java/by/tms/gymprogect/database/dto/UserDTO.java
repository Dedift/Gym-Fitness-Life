package by.tms.gymprogect.database.dto;

import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.Train.TrainingDay;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Review;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.UserData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDTO {

    private String email;
    private String password;
    private Gender gender;
    private List<TrainingDay> trainingProgram;
    private List<Subscription> subscriptions;
    private LocalDate dateOfBirth;
    private UserData userData;
    private Role role;
    private PersonalTrainer personalTrainer;
    private Order order;
    private Review review;
}
