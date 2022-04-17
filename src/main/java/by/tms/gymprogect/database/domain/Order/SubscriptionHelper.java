package by.tms.gymprogect.database.domain.Order;

import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.User.User;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class SubscriptionHelper {

    /**
     * Calculates the number of workouts for a new subscription, taking into account the previous one
     */
    public static int mathCountRemainingTrain(int countTrain, User user) {
        int result = -1;
        List<Subscription> subscriptions = user.getSubscriptions();
        Subscription actualSubscription = !subscriptions.isEmpty() ?
                subscriptions.get(subscriptions.size() - Number.ONE) : null;
        if (Objects.nonNull(actualSubscription)) {
            result = actualSubscription.getCountRemainingTrain() + countTrain;
            actualSubscription.setCountRemainingTrain(Number.ZERO);
        } else {
            result = countTrain;
        }
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
        Season season = order.getSeason();
        return switch (season) {
            case MONTH -> date.plusMonths(Number.ONE);
            case SIX_MONTHS -> date.plusMonths(Number.SIX);
            case YEAR -> date.plusYears(Number.ONE);
            default -> date;
        };
    }
}
