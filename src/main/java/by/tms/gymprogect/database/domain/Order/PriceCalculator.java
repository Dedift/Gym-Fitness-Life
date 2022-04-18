package by.tms.gymprogect.database.domain.Order;

import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.User.User;

import lombok.experimental.UtilityClass;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@UtilityClass
public final class PriceCalculator {

    private static final String SEASON_FOR_MATH_PRICE = "Season for math the price is: {}";
    private static final String PRICE_WITHOUT_SALE = "Price without sale is: {}";
    private static final String SALE = "Sale is: {}";
    private static final String PRICE_WITH_SALE = "Price with sale is: {}";
    private static final String COUNT_TRAINS_FOR_MATH_SALE = "Count trains for math sale is: {}";
    private static final String USER_HAS_SUBSCRIPTIONS = "User has {} subscriptions";
    private Logger logger = LogManager.getLogger(PriceCalculator.class);

    /**
     * Math and get an orders price by season, considering the discount for regular customers
     */
    public static int mathPriceBySeason(Season season, User user) {
        int price = -Number.ONE;
        logger.debug(SEASON_FOR_MATH_PRICE, season);
        switch (season) {
            case MONTH -> price = Number.PRICE_FOR_MONTH;
            case SIX_MONTHS -> price = Number.PRICE_FOR_SIX_MONTHS;
            case YEAR -> price = Number.PRICE_FOR_YEAR;
        }
        logger.debug(PRICE_WITHOUT_SALE, price);
        int sale = mathSaleForRegularClient(price, user);
        logger.debug(SALE, sale);
        int priceWithSale = price - mathSaleForRegularClient(price, user);
        logger.debug(PRICE_WITH_SALE, priceWithSale);
        return priceWithSale;
    }

    /**
     * Math and get an orders price by count trains, considering the discount for regular customers
     */
    public static int mathPriceByCountTrain(int countTrain, User user) {
        logger.debug(COUNT_TRAINS_FOR_MATH_SALE, countTrain);
        int price = countTrain * Number.PRICE_ONE_TRAIN;
        logger.debug(PRICE_WITHOUT_SALE, price);
        int sale = mathSaleByCountTrain(price) + mathSaleForRegularClient(price, user);
        logger.debug(SALE, sale);
        int priceWithSale = price - sale;
        logger.debug(PRICE_WITH_SALE, priceWithSale);
        return priceWithSale;
    }

    private int mathSaleByCountTrain(int price) {
        return (int) (price * Number.SALE_BY_COUNT_TRAIN);
    }

    private int mathSaleForRegularClient(int price, User user) {
        int countSubscriptions = user.getSubscriptions().size();
        logger.debug(USER_HAS_SUBSCRIPTIONS, countSubscriptions);
        return countSubscriptions >= Number.TEN ?
                (int) (price * Number.MAX_SALE_FOR_REGULAR_CLIENT) :
                (int) (price * user.getSubscriptions().size() * Number.SALE_FOR_REGULAR_CLIENT);
    }
}
