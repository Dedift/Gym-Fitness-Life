package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.OrderDao;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.OrderDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private static final String DELETE_ORDER = "Delete order: {}";
    private static final String ACCEPTED_TO_DELETE = "Accepted to delete orderDTO: {}";
    private static final String UPDATE_ORDER = "Update order: {}";
    private static final String ACCEPTED_TO_UPDATE = "Accepted to update orderDTO: {}";
    private static final String FIND_ORDERS_WORKOUTS_LESS = "Find orders: {} in which the number of workouts is less: {}";
    private static final String FIND_ORDERS_WORKOUTS_MORE = "Find orders: {} in which the number of workouts is more: {}";
    private static final String FIND_ORDERS_BY_PURPOSE = "Find orders: {} by purpose: {}";
    private static final String FIND_ORDERS_BY_SEASON = "Find orders: {} by season: {}";
    private static final String FIND_ORDER_BY_ID = "Find order: {} by id: {}";
    private static final String FIND_ALL_ORDERS = "Find all orders: {}";
    private static final String SAVE_ORDER_WITH_ID = "Save order: {} with id: {}";
    private static final String ACCEPTED_TO_SAVE_ORDER_DTO = "Accepted to save orderDTO: {}";
    private OrderDao orderDao;
    private Logger logger = LogManager.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Accept the orderDTO, map to an order, save, and return its primary key
     */
    @Transactional
    public Integer save(OrderDTO orderDTO) {
        logger.debug(ACCEPTED_TO_SAVE_ORDER_DTO, orderDTO);
        Order order = ModelMapper.map(orderDTO, Order.class);
        Integer id = orderDao.save(order);
        logger.info(SAVE_ORDER_WITH_ID, order, id);
        return id;
    }

    /**
     * Find all orders, map to DTOs, and get
     */
    public List<OrderDTO> findAll() {
        List<Order> orders = orderDao.findAll();
        logger.info(FIND_ALL_ORDERS, orders);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    /**
     * Find an order by id, map to DTO, and get
     */
    public Optional<OrderDTO> findById(Integer id) {
        Optional<Order> maybeOrder = orderDao.findById(id);
        OrderDTO orderDTO = OrderDTO.builder().build();
        if (maybeOrder.isPresent()) {
            Order order = maybeOrder.get();
            logger.info(FIND_ORDER_BY_ID, order, id);
            orderDTO = ModelMapper.map(order, OrderDTO.class);
        }
        return Optional.ofNullable(orderDTO);
    }

    /**
     * Find all orders by season, map to DTOs, and get
     */
    public List<OrderDTO> findBySeason(Season season) {
        List<Order> orders = orderDao.findBySeason(season);
        logger.info(FIND_ORDERS_BY_SEASON, orders, season);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    /**
     * Find all orders by purpose, map to DTOs, and get
     */
    public List<OrderDTO> findByPurpose(Purpose purpose) {
        List<Order> orders = orderDao.findByPurpose(purpose);
        logger.info(FIND_ORDERS_BY_PURPOSE, orders, purpose);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    /**
     * Find orders in which the number of workouts is more than the received value, map to DTOs, and get
     */
    public List<OrderDTO> findOrdersWhereCountTrainMore(Integer countTrain) {
        List<Order> orders = orderDao.findOrdersWhereCountTrainMore(countTrain);
        logger.info(FIND_ORDERS_WORKOUTS_MORE, orders, countTrain);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    /**
     * Find orders in which the number of workouts is less than the received value, map to DTOs, and get
     */
    public List<OrderDTO> findOrdersWhereCountTrainLess(Integer countTrain) {
        List<Order> orders = orderDao.findOrdersWhereCountTrainLess(countTrain);
        logger.info(FIND_ORDERS_WORKOUTS_LESS, orders, countTrain);
        return ModelMapper.mapAll(orders, OrderDTO.class);
    }

    /**
     * Accept the orderDTO, map to an order, and update
     */
    @Transactional
    public void update(OrderDTO orderDTO) {
        logger.debug(ACCEPTED_TO_UPDATE, orderDTO);
        Order order = ModelMapper.map(orderDTO, Order.class);
        orderDao.update(order);
        logger.info(UPDATE_ORDER, order);
    }

    /**
     * Accept the orderDTO, map to an order, and delete
     */
    @Transactional
    public void delete(OrderDTO orderDTO) {
        logger.debug(ACCEPTED_TO_DELETE, orderDTO);
        Order order = ModelMapper.map(orderDTO, Order.class);
        orderDao.delete(order);
        logger.info(DELETE_ORDER, order);
    }
}
