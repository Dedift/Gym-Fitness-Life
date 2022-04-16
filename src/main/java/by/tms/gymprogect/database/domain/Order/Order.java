package by.tms.gymprogect.database.domain.Order;

import by.tms.gymprogect.database.domain.BaseEntity;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.domain.User.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "gym_schema", name = "orders")
public class Order extends BaseEntity<Integer> {

    private int countTrain;
    @Enumerated(EnumType.STRING)
    private Purpose purpose;
    @Enumerated(EnumType.STRING)
    private Season season;
    private int price;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Subscription subscription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    public Order(int countTrain, Purpose purpose, Season season, int price, Subscription subscription, User user) {
        this.countTrain = countTrain;
        this.purpose = purpose;
        this.season = season;
        if(Objects.nonNull(user)) {
            this.price = Objects.nonNull(season) ?
                    PriceCalculator.mathPriceBySeason(season, user) : PriceCalculator.mathPriceByCountTrain(countTrain, user);
        }
        this.user = user;
    }
}
