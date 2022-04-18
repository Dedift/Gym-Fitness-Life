package by.tms.gymprogect.database.dto;

import by.tms.gymprogect.database.domain.User.Gender;
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
public class UserDTO extends BaseDTO<Integer> {

    private String email;
    private String password;
    private Gender gender;
    private List<TrainingDayDTO> trainingProgramDTO;
    private List<SubscriptionDTO> subscriptionsDTO;
    private LocalDate dateOfBirth;
    private UserData userData;
    private Role role;
    private PersonalTrainerDTO personalTrainerDTO;
    private List<OrderDTO> ordersDTO;
    private ReviewDTO reviewDTO;
}
