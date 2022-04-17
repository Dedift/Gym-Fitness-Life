package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.dao.SubscriptionDao;
import by.tms.gymprogect.database.domain.Order.Subscription;

import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionDaoImpl extends BaseDAOImpl<Integer, Subscription> implements SubscriptionDao {
}