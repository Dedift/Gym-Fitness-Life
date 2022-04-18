package by.tms.gymprogect.database.dto;

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
public class ReviewDTO extends BaseDTO<Integer> {

    private String text;
    @ToString.Exclude
    private UserDTO userDTO;
}