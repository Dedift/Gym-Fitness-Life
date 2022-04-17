package by.tms.gymprogect.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TrainingDayDTO extends BaseDTO<Integer> {

    private int countSetsPerExercise;
    private int countRepetitionsPerSet;
    private List<ExerciseDTO> exercisesDTO;
    @ToString.Exclude
    private UserDTO userDTO;
}