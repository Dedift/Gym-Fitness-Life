package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.OrderDao;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.OrderDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Transactional
    public Integer save(OrderDTO orderDTO) {
        Order order = ModelMapper.map(orderDTO, Order.class);
        return orderDao.save(order);
    }

    public List<OrderDTO> findAll() {
        List<Order> orders = orderDao.findAll();
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    public Optional<OrderDTO> findById(Integer id) {
        Optional<Order> maybeOrder = orderDao.findById(id);
        OrderDTO orderDTO = OrderDTO.builder().build();
        if (maybeOrder.isPresent()) {
            Order order = maybeOrder.get();
            orderDTO = ModelMapper.map(order, OrderDTO.class);
        }
        return Optional.ofNullable(orderDTO);
    }

    public List<OrderDTO> findBySeason(Season season) {
        List<Order> orders = orderDao.findBySeason(season);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    public List<OrderDTO> findByPurpose(Purpose purpose) {
        List<Order> orders = orderDao.findByPurpose(purpose);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    public List<OrderDTO> findOrdersWhereCountTrainMore(Integer countTrain) {
        List<Order> orders = orderDao.findOrdersWhereCountTrainMore(countTrain);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    public List<OrderDTO> findOrdersWhereCountTrainLess(Integer countTrain) {
        List<Order> orders = orderDao.findOrdersWhereCountTrainLess(countTrain);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    @Transactional
    public void update(OrderDTO orderDTO) {
        Order order = ModelMapper.map(orderDTO, Order.class);
        orderDao.update(order);
    }

    @Transactional
    public void delete(OrderDTO orderDTO) {
        Order order = ModelMapper.map(orderDTO, Order.class);
        orderDao.delete(order);
    }
}
