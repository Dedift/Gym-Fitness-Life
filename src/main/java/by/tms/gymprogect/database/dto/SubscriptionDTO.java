package by.tms.gymprogect.database.dto;

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
public class SubscriptionDTO extends BaseDTO<Integer> {

    private LocalDate timeOfAction;
    private int countRemainingTrain;
    @ToString.Exclude
    private OrderDTO orderDTO;
    @ToString.Exclude
    private UserDTO userDTO;
}