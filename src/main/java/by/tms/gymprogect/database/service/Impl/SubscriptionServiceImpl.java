package by.tms.gymprogect.database.service.Impl;

import by.tms.gymprogect.database.dao.SubscriptionDao;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.SubscriptionDTO;
import by.tms.gymprogect.database.service.SubscriptionService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String DELETE_SUBSCRIPTION = "Delete subscription: {}";
    private static final String ACCEPTED_TO_DELETE = "Accepted to delete subscriptionDTO: {}";
    private static final String UPDATE_SUBSCRIPTION = "Update subscription: {}";
    private static final String ACCEPTED_TO_UPDATE = "Accepted to update subscriptionDTO: {}";
    private static final String FIND_SUBSCRIPTION_BY_ID = "Find subscription: {} by id: {}";
    private static final String FIND_ALL_SUBSCRIPTIONS = "Find all subscriptions: {}";
    private static final String SAVE_SUBSCRIPTION_WITH_ID = "Save subscription: {} with id: {}";
    private static final String ACCEPTED_TO_SAVE = "Accepted to save subscriptionDTO: {}";
    private SubscriptionDao subscriptionDao;
    private Logger logger = LogManager.getLogger(SubscriptionService.class);

    @Autowired
    public SubscriptionServiceImpl(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    /**
     * Accept the subscriptionDTO, map to a subscription, save, and return its primary key
     */
    @Override
    @Transactional
    public Integer save(SubscriptionDTO subscriptionDTO) {
        logger.debug(ACCEPTED_TO_SAVE, subscriptionDTO);
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        Integer id = subscriptionDao.save(subscription);
        logger.debug(SAVE_SUBSCRIPTION_WITH_ID, subscription, id);
        return id;
    }

    /**
     * Find all subscriptions, map to DTOs, and get
     */
    @Override
    public List<SubscriptionDTO> findAll() {
        List<Subscription> subscriptions = subscriptionDao.findAll();
        logger.debug(FIND_ALL_SUBSCRIPTIONS, subscriptions);
        return ModelMapper.mapAll(subscriptions, SubscriptionDTO.class);
    }

    /**
     * Find a subscription by id, map to DTO, and get
     */
    @Override
    public Optional<SubscriptionDTO> findById(Integer id) {
        Optional<Subscription> maybeSubscription = subscriptionDao.findById(id);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder().build();
        if (maybeSubscription.isPresent()) {
            Subscription subscription = maybeSubscription.get();
            logger.debug(FIND_SUBSCRIPTION_BY_ID, subscription, id);
            subscriptionDTO = ModelMapper.map(subscription, SubscriptionDTO.class);
        }
        return Optional.ofNullable(subscriptionDTO);
    }

    /**
     * Accept the subscriptionDTO, map to a subscription, and update
     */
    @Override
    @Transactional
    public void update(SubscriptionDTO subscriptionDTO) {
        logger.debug(ACCEPTED_TO_UPDATE, subscriptionDTO);
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        subscriptionDao.update(subscription);
        logger.debug(UPDATE_SUBSCRIPTION, subscription);
    }

    /**
     * Accept the subscriptionDTO, map to a subscription, and delete
     */
    @Override
    @Transactional
    public void delete(SubscriptionDTO subscriptionDTO) {
        logger.debug(ACCEPTED_TO_DELETE, subscriptionDTO);
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        subscriptionDao.delete(subscription);
        logger.debug(DELETE_SUBSCRIPTION, subscription);
    }
}
