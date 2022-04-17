package by.tms.gymprogect.database.domain.Order;

import by.tms.gymprogect.database.domain.BaseEntity;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.User.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "gym_schema")
public class Subscription extends BaseEntity<Integer> {

    private LocalDate timeOfAction;
    private int countRemainingTrain;
    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "subscription")
    @ToString.Exclude
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    public Subscription(LocalDate timeOfAction, int countTrain, Order order, User user) {
        this.order = order;
        this.user = user;
        if(order.getCountTrain() > Number.ZERO) {
            this.countRemainingTrain = SubscriptionHelper.mathCountRemainingTrain(order.getCountTrain(), user);
        } else {
            this.timeOfAction = SubscriptionHelper.mathTimeOfAction(order, user);
        }
    }
}
