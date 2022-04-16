package by.tms.gymprogect.database.dao;

import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Train.Purpose;

import java.util.List;

public interface OrderDao extends BaseDAO<Integer, Order>{

    List<Order> findBySeason(Season season);

    List<Order> findByPurpose(Purpose purpose);

    List<Order> findOrdersWhereCountTrainMore(Integer countTrain);

    List<Order> findOrdersWhereCountTrainLess(Integer countTrain);
}
