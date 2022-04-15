package by.tms.gymprogect.database.domain.Train;

import by.tms.gymprogect.database.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
@Table(schema = "gym_schema")
public class Exercise extends BaseEntity<Integer> {
    @Column(unique = true)
    private String name;
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "exercises")
    @Builder.Default
    private List<TrainingDay> trainingDays = new ArrayList<>();
    }