package by.tms.gymprogect.database.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Embeddable
@Table(schema = "gym_schema")
public class UserData {

    private String bankCard;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}