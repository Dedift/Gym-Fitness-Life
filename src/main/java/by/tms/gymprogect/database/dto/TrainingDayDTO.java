package by.tms.gymprogect.database.dto;

import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.domain.User.User;

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
public class TrainingDayDTO {

    private int countSetsPerExercise;
    private int countRepetitionsPerSet;
    private List<Exercise> exercises;
    private User user;
}