package by.tms.gymprogect.database.domain.Order;

import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.User.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class PriceCalculator {

    /**
     * Math and get an orders price by season, considering the discount for regular customers
     */
    public static int mathPriceBySeason(Season season, User user) {
        int price = -Number.ONE;
        switch (season) {
            case MONTH -> price = Number.PRICE_FOR_MONTH;
            case SIX_MONTHS -> price = Number.PRICE_FOR_SIX_MONTHS;
            case YEAR -> price = Number.PRICE_FOR_YEAR;
        }
        return price - mathSaleForRegularClient(price, user);
    }

    /**
     * Math and get an orders price by count trains, considering the discount for regular customers
     */
    public static int mathPriceByCountTrain(int countTrain, User user) {
        int price = countTrain * Number.PRICE_ONE_TRAIN;
        return price - (mathSaleByCountTrain(price) + mathSaleForRegularClient(price, user));
    }

    private int mathSaleByCountTrain(int price) {
        return (int) (price * Number.SALE_BY_COUNT_TRAIN);
    }

    private int mathSaleForRegularClient(int price, User user) {
        return user.getSubscriptions().size() >= Number.TEN ?
                (int) (price * Number.MAX_SALE_FOR_REGULAR_CLIENT) :
                (int) (price * user.getSubscriptions().size() * Number.SALE_FOR_REGULAR_CLIENT);
    }
}
