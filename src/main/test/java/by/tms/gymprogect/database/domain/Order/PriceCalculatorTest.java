package by.tms.gymprogect.database.domain.Order;

import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.domain.User.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PriceCalculatorTest {

    protected static final String ANY_EMAIL_GMAIL_COM = "anyemail@gmail.com";

    @Test
    void mathPriceBySeason() {
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
                .build(),
                Subscription.builder()
                        .user(user)
                        .order(usersOrder)
                        .build(),
                Subscription.builder()
                        .user(user)
                        .order(usersOrder)
                        .build()));
        int price = PriceCalculator.mathPriceBySeason(Season.SIX_MONTHS, user);
        Assertions.assertEquals(Number.THREE_HUNDRED_SEVENTY_FIVE, price);
    }

    @Test
    void mathPriceByCountTrain() {
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
                        .build(),
                Subscription.builder()
                        .user(user)
                        .order(usersOrder)
                        .build(),
                Subscription.builder()
                        .user(user)
                        .order(usersOrder)
                        .build()));
        int price = PriceCalculator.mathPriceByCountTrain(Number.THIRTY, user);
        Assertions.assertEquals(Number.ONE_HUNDRED_EIGHTY_SIX, price);
    }
}