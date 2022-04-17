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
public class ExerciseDTO extends BaseDTO<Integer> {

    private String name;
    @ToString.Exclude
    private List<TrainingDayDTO> trainingDaysDTO;
}