package by.tms.gymprogect.database.domain.Train;

import by.tms.gymprogect.database.domain.BaseEntity;
import by.tms.gymprogect.database.domain.User.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "gym_schema", name = "training_day")
public class TrainingDay extends BaseEntity<Integer> {
    private int countSetsPerExercise;
    private int countRepetitionsPerSet;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "training_day_exercises",
            schema = "gym_schema",
            joinColumns = @JoinColumn(name = "training_day_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    @Builder.Default
    private List<Exercise> exercises = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}

