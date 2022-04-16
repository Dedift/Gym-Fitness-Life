package by.tms.gymprogect.database.dto;

import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.domain.User.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderDTO {

    private int countTrain;
    private Purpose purpose;
    private Season season;
    private int price;
    private Subscription subscription;
    private User user;
}