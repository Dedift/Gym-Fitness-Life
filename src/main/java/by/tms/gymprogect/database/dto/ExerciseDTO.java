package by.tms.gymprogect.database.dto;

import by.tms.gymprogect.database.domain.Train.TrainingDay;

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
public class ExerciseDTO {

    private String name;
    private List<TrainingDay> trainingDays;
}