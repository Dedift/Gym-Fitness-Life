package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.TrainingDayDao;
import by.tms.gymprogect.database.domain.Train.TrainingDay;

import org.springframework.stereotype.Repository;

@Repository
public class TrainingDayDaoImpl extends BaseDAOImpl<Integer, TrainingDay> implements TrainingDayDao {
}