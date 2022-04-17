package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.SubscriptionDao;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.SubscriptionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SubscriptionService {

    private SubscriptionDao subscriptionDao;

    @Autowired
    public SubscriptionService(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    /**
     * Accept the subscriptionDTO, map to a subscription, save, and return its primary key
     */
    @Transactional
    public Integer save(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        return subscriptionDao.save(subscription);
    }

    /**
     * Find all subscriptions, map to DTOs, and get
     */
    public List<SubscriptionDTO> findAll() {
        List<Subscription> subscriptions = subscriptionDao.findAll();
        return ModelMapper.mapAll(subscriptions, SubscriptionDTO.class);
    }

    /**
     * Find a subscription by id, map to DTO, and get
     */
    public Optional<SubscriptionDTO> findById(Integer id) {
        Optional<Subscription> maybeSubscription = subscriptionDao.findById(id);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder().build();
        if (maybeSubscription.isPresent()) {
            Subscription subscription = maybeSubscription.get();
            subscriptionDTO = ModelMapper.map(subscription, SubscriptionDTO.class);
        }
        return Optional.ofNullable(subscriptionDTO);
    }

    /**
     * Accept the subscriptionDTO, map to a subscription, and update
     */
    @Transactional
    public void update(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        subscriptionDao.update(subscription);
    }

    /**
     * Accept the subscriptionDTO, map to a subscription, and delete
     */
    @Transactional
    public void delete(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        subscriptionDao.delete(subscription);
    }
}
