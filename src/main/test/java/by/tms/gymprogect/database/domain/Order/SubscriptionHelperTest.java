package by.tms.gymprogect.database.domain.Order;

import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.domain.User.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class SubscriptionHelperTest {

    protected static final String ANY_EMAIL_GMAIL_COM = "anyemail@gmail.com";

    @Test
    void mathCountRemainingTrain() {
        User user = User.builder()
                .email(ANY_EMAIL_GMAIL_COM)
                .build();
        Order usersOrder = Order.builder()
                .countTrain(Number.TWELVE)
                .purpose(Purpose.MUSCLE)
                .user(user)
                .build();
        user.getOrders().add(usersOrder);
        user.setSubscriptions(List.of(Subscription.builder()
                        .user(user)
                        .order(usersOrder)
                        .build()));
        int remainingTrain = SubscriptionHelper.mathCountRemainingTrain(Number.THIRTY, user);
        Assertions.assertEquals(Number.FORTY_TWO, remainingTrain);
    }

    @Test
    void mathTimeOfAction() {
        User user = User.builder()
                .email(ANY_EMAIL_GMAIL_COM)
                .build();
        Order usersOrder = Order.builder()
                .season(Season.SIX_MONTHS)
                .purpose(Purpose.MUSCLE)
                .user(user)
                .build();
        user.getOrders().add(usersOrder);
        user.setSubscriptions(List.of(Subscription.builder()
                .user(user)
                .order(usersOrder)
                .build()));
        LocalDate actualTimeOfAction = SubscriptionHelper.mathTimeOfAction(usersOrder, user);
        Assertions.assertEquals(LocalDate.now().plusYears(Number.ONE), actualTimeOfAction);
    }
}