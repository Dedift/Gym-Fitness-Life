package by.tms.gymprogect.database.dao;

import by.tms.gymprogect.database.domain.Train.Exercise;

import java.util.Optional;

public interface ExerciseDao extends BaseDAO<Integer, Exercise> {

    Optional<Exercise> findByName(String name);
}
