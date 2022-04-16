package by.tms.gymprogect.database.dto;

import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.User.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SubscriptionDTO {

    private LocalDate timeOfAction;
    private int countRemainingTrain;
    private Order order;
    private User user;
}