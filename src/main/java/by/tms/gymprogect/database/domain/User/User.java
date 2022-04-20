package by.tms.gymprogect.database.domain.User;

import by.tms.gymprogect.database.domain.BaseEntity;
import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.Train.TrainingDay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "gym_schema", name = "users")
public class User extends BaseEntity<Integer> {

    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Builder.Default
    private List<TrainingDay> trainingProgram = new ArrayList<>();
    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Builder.Default
    private List<Subscription> subscriptions = new ArrayList<>();
    @Embedded
    private UserData userData;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_trainer_id")
    private PersonalTrainer personalTrainer;
    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();
    @Cascade(CascadeType.SAVE_UPDATE)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Review review;
}