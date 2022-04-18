package by.tms.gymprogect.database.dto;

import by.tms.gymprogect.database.domain.User.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PersonalTrainerDTO extends BaseDTO<Integer> {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private int experience;
    @ToString.Exclude
    private Set<UserDTO> wards;
}