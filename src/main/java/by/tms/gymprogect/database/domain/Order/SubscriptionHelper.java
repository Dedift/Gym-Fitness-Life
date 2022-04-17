package by.tms.gymprogect.database.domain.Order;

import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.User.User;

import lombok.experimental.UtilityClass;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class SubscriptionHelper {

    private static final String COUNT_TRAIN_FOR_CREATE_SUBSCRIPTION = "Count train for create subscription is: {}";
    private static final String COUNT_REMAINING_TRAININGS_IN_THE_LAST_SUBSCRIPTION = "Count remaining trainings in the last subscription: {}";
    private static final String COUNT_TRAININGS_IN_NEW_SUBSCRIPTION = "Count trainings in new subscription: {}";
    private static final String THE_VALIDITY_PERIOD_OF_THE_PREVIOUS_SUBSCRIPTION = "The validity period of the previous subscription: {}";
    private static final String THE_VALIDITY_PERIOD_IN_NEW_SUBSCRIPTION = "The validity period in new subscription: {}";
    private Logger logger = LogManager.getLogger(SubscriptionHelper.class);

    /**
     * Calculates the number of workouts for a new subscription, taking into account the previous one
     */
    public static int mathCountRemainingTrain(int countTrain, User user) {
        int result = -1;
        logger.debug(COUNT_TRAIN_FOR_CREATE_SUBSCRIPTION, countTrain);
        List<Subscription> subscriptions = user.getSubscriptions();
        Subscription actualSubscription = !subscriptions.isEmpty() ?
                subscriptions.get(subscriptions.size() - Number.ONE) : null;
        if (Objects.nonNull(actualSubscription)) {
            int countRemainingTrain = actualSubscription.getCountRemainingTrain();
            logger.debug(COUNT_REMAINING_TRAININGS_IN_THE_LAST_SUBSCRIPTION, countRemainingTrain);
            result = countRemainingTrain + countTrain;
            actualSubscription.setCountRemainingTrain(Number.ZERO);
        } else {
            result = countTrain;
        }
        logger.debug(COUNT_TRAININGS_IN_NEW_SUBSCRIPTION, result);
        return result;
    }

    /**
     * Calculates the time of action for a new subscription, taking into account the previous one
     */
    public static LocalDate mathTimeOfAction(Order order, User user) {
        LocalDate date = LocalDate.now();
        List<Subscription> subscriptions = user.getSubscriptions();
        Subscription actualSubscription = !subscriptions.isEmpty() ?
                subscriptions.get(subscriptions.size() - Number.ONE) : null;
        if (Objects.nonNull(actualSubscription) &&
                Objects.nonNull(actualSubscription.getTimeOfAction()) &&
                actualSubscription.getTimeOfAction().isAfter(LocalDate.now())) {
            date = actualSubscription.getTimeOfAction();
            actualSubscription.setTimeOfAction(LocalDate.now());
        }
        logger.debug(THE_VALIDITY_PERIOD_OF_THE_PREVIOUS_SUBSCRIPTION, date);
        Season season = order.getSeason();
        switch (season) {
            case MONTH -> date = date.plusMonths(Number.ONE);
            case SIX_MONTHS -> date = date.plusMonths(Number.SIX);
            case YEAR -> date = date.plusYears(Number.ONE);
        }
        logger.debug(THE_VALIDITY_PERIOD_IN_NEW_SUBSCRIPTION, date);
        return date;
    }
}
