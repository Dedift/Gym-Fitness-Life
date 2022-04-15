package by.tms.gymprogect.database.domain.Train;

import by.tms.gymprogect.database.domain.BaseEntity;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "gym_schema", name = "personal_trainer")
public class PersonalTrainer extends BaseEntity<Integer> {

    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int experience;
    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "personalTrainer")
    @Builder.Default
    @ToString.Exclude
    private Set<User> wards = new HashSet<>();

}
