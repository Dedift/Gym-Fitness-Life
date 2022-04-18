package by.tms.gymprogect.database.service.Impl;

import by.tms.gymprogect.database.dao.SubscriptionDao;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.SubscriptionDTO;
import by.tms.gymprogect.database.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionDao subscriptionDao;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    @Transactional
    public Integer save(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        return subscriptionDao.save(subscription);
    }

    @Override
    public List<SubscriptionDTO> findAll() {
        List<Subscription> subscriptions = subscriptionDao.findAll();
        return ModelMapper.mapAll(subscriptions, SubscriptionDTO.class);
    }

    @Override
    public Optional<SubscriptionDTO> findById(Integer id) {
        Optional<Subscription> maybeSubscription = subscriptionDao.findById(id);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder().build();
        if (maybeSubscription.isPresent()) {
            Subscription subscription = maybeSubscription.get();
            subscriptionDTO = ModelMapper.map(subscription, SubscriptionDTO.class);
        }
        return Optional.ofNullable(subscriptionDTO);
    }

    @Override
    @Transactional
    public void update(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        subscriptionDao.update(subscription);
    }

    @Override
    @Transactional
    public void delete(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = ModelMapper.map(subscriptionDTO, Subscription.class);
        subscriptionDao.delete(subscription);
    }
}
