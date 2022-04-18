package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.dto.OrderDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    @Transactional
    Integer save(OrderDTO orderDTO);

    List<OrderDTO> findAll();

    Optional<OrderDTO> findById(Integer id);

    List<OrderDTO> findBySeason(Season season);

    List<OrderDTO> findByPurpose(Purpose purpose);

    List<OrderDTO> findOrdersWhereCountTrainMore(Integer countTrain);

    List<OrderDTO> findOrdersWhereCountTrainLess(Integer countTrain);

    @Transactional
    void update(OrderDTO orderDTO);

    @Transactional
    void delete(OrderDTO orderDTO);
}
