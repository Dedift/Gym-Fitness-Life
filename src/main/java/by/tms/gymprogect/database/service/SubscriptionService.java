package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dto.SubscriptionDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {

    @Transactional
    Integer save(SubscriptionDTO subscriptionDTO);

    List<SubscriptionDTO> findAll();

    Optional<SubscriptionDTO> findById(Integer id);

    @Transactional
    void update(SubscriptionDTO subscriptionDTO);

    @Transactional
    void delete(SubscriptionDTO subscriptionDTO);
}
