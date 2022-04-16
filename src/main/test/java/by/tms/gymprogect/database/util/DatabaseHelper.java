package by.tms.gymprogect.database.util;

import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Season;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.domain.Train.Exercise_;
import by.tms.gymprogect.database.domain.Train.ExercisesName;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.Train.Purpose;
import by.tms.gymprogect.database.domain.Train.TrainingDay;
import by.tms.gymprogect.database.domain.User.Gender;
import by.tms.gymprogect.database.domain.User.Review;
import by.tms.gymprogect.database.domain.User.Role;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.domain.User.UserData;

import org.hibernate.Session;

import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.time.LocalDate;

import java.util.List;

@Component
public class DatabaseHelper {


    public void cleanDatabase(Session session) {
        session.createQuery("delete from Exercise ").executeUpdate();
        session.createQuery("delete from Order ").executeUpdate();
        session.createQuery("delete from PersonalTrainer ").executeUpdate();
        session.createQuery("delete from Review ").executeUpdate();
        session.createQuery("delete from Subscription ").executeUpdate();
        session.createQuery("delete from TrainingDay ").executeUpdate();
        session.createQuery("delete from User").executeUpdate();
    }

    public void prepareData(Session session) {
        prepareExercises(session);
        PersonalTrainer olgaNovikova = PersonalTrainer.builder()
                .firstName("Olga")
                .lastName("Novikova")
                .experience(4)
                .gender(Gender.FEMALE)
                .build();
        PersonalTrainer olegPristavko = PersonalTrainer.builder()
                .firstName("Oleg")
                .lastName("Pristavko")
                .experience(2)
                .gender(Gender.MALE)
                .build();
        User svetaPopova = User.builder()
                .email("Popova@gmail.com")
                .password("pass")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(2000, 4, 12))
                .userData(UserData.builder()
                        .bankCard("234214324")
                        .firstName("Sveta")
                        .lastName("Popova")
                        .phoneNumber("+375294432576")
                        .build())
                .role(Role.USER)
                .review(Review.builder()
                        .text("I'm very happy")
                        .build())
                .personalTrainer(olgaNovikova)
                .build();
        Order svetasOrder = Order.builder()
                .season(Season.SIX_MONTHS)
                .purpose(Purpose.MUSCLE)
                .user(svetaPopova)
                .build();
        svetaPopova.getOrders().add(svetasOrder);
        svetaPopova.getSubscriptions().add(Subscription.builder()
                .user(svetaPopova)
                .order(svetasOrder)
                .build());
        svetaPopova.setTrainingProgram(List.of(
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.SHRUGS, session),
                                findExerciseByName(ExercisesName.CHEST_PRESS_MACHINE, session),
                                findExerciseByName(ExercisesName.BACK_EXTENSION, session)))
                        .countSetsPerExercise(3)
                        .countRepetitionsPerSet(12)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.BACK_SQUAT, session),
                                findExerciseByName(ExercisesName.CABLE_PUSH_DOWN, session),
                                findExerciseByName(ExercisesName.REVERSE_CRUNCH, session)))
                        .countSetsPerExercise(3)
                        .countRepetitionsPerSet(12)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.BOX_JUMPS, session),
                                findExerciseByName(ExercisesName.CLOSE_GRIP_BENCH_PRESS, session),
                                findExerciseByName(ExercisesName.BICYCLE_CRUNCH_SITTING, session)))
                        .countSetsPerExercise(3)
                        .countRepetitionsPerSet(12)
                        .build()));
        User pavelLetov = User.builder()
                .email("Pavel@mail.ru")
                .password("pass")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(2002, 7, 28))
                .userData(UserData.builder()
                        .bankCard("87396729")
                        .firstName("Pavel")
                        .lastName("Letov")
                        .phoneNumber("+375448975632")
                        .build())
                .role(Role.USER)
                .review(Review.builder()
                        .text("This is the best gym!")
                        .build())
                .personalTrainer(olgaNovikova)
                .build();
        Order pavelsOrder = Order.builder()
                .countTrain(36)
                .purpose(Purpose.FAT_BURNING)
                .user(pavelLetov)
                .build();
        pavelLetov.getOrders().add(pavelsOrder);
        pavelLetov.getSubscriptions().add(Subscription.builder()
                .user(pavelLetov)
                .order(pavelsOrder)
                .build());
        pavelLetov.setTrainingProgram(List.of(
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.LOW_CABLE_CROSSOVER, session),
                                findExerciseByName(ExercisesName.HAMMER_CURL, session),
                                findExerciseByName(ExercisesName.SKATERS, session)))
                        .countSetsPerExercise(4)
                        .countRepetitionsPerSet(20)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.LIFTING_DUMBBELLS_IN_FRONT_OF_YOU, session),
                                findExerciseByName(ExercisesName.CABLE_PUSH_DOWN, session),
                                findExerciseByName(ExercisesName.REVERSE_CRUNCH, session)))
                        .countSetsPerExercise(4)
                        .countRepetitionsPerSet(20)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.BOX_JUMPS, session),
                                findExerciseByName(ExercisesName.CLOSE_GRIP_BENCH_PRESS, session),
                                findExerciseByName(ExercisesName.BICYCLE_CRUNCH_SITTING, session)))
                        .countSetsPerExercise(4)
                        .countRepetitionsPerSet(20)
                        .build()));
        User nikitaKlimovich = User.builder()
                .email("nikitaKlimovich@mail.ru")
                .password("pass")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1992, 4, 8))
                .userData(UserData.builder()
                        .bankCard("87396729")
                        .firstName("Nikita")
                        .lastName("Klimovich")
                        .phoneNumber("+375259963298")
                        .build())
                .role(Role.USER)
                .review(Review.builder()
                        .text("This gym is wonderful!")
                        .build())
                .personalTrainer(olegPristavko)
                .build();
        Order niksOrder = Order.builder()
                .countTrain(12)
                .purpose(Purpose.MUSCLE)
                .user(nikitaKlimovich)
                .build();
        nikitaKlimovich.getOrders().add(niksOrder);
        nikitaKlimovich.getSubscriptions().add(Subscription.builder()
                        .user(nikitaKlimovich)
                        .order(niksOrder)
                .build());
        nikitaKlimovich.setTrainingProgram(List.of(
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.LOW_CABLE_CROSSOVER, session),
                                findExerciseByName(ExercisesName.HAMMER_CURL, session),
                                findExerciseByName(ExercisesName.SKATERS, session)))
                        .countSetsPerExercise(4)
                        .countRepetitionsPerSet(15)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.LIFTING_DUMBBELLS_IN_FRONT_OF_YOU, session),
                                findExerciseByName(ExercisesName.CABLE_PUSH_DOWN, session),
                                findExerciseByName(ExercisesName.REVERSE_CRUNCH, session)))
                        .countSetsPerExercise(4)
                        .countRepetitionsPerSet(15)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.BOX_JUMPS, session),
                                findExerciseByName(ExercisesName.CLOSE_GRIP_BENCH_PRESS, session),
                                findExerciseByName(ExercisesName.BICYCLE_CRUNCH_SITTING, session)))
                        .countSetsPerExercise(4)
                        .countRepetitionsPerSet(25)
                        .build()));
        olgaNovikova.getWards().add(svetaPopova);
        olgaNovikova.getWards().add(pavelLetov);
        olegPristavko.getWards().add(nikitaKlimovich);
        session.save(olgaNovikova);
        session.save(olegPristavko);
    }

    private void prepareExercises(Session session){
        session.save(Exercise.builder().name(ExercisesName.SHRUGS).build());
        session.save(Exercise.builder().name(ExercisesName.CHEST_PRESS_MACHINE).build());
        session.save(Exercise.builder().name(ExercisesName.BACK_EXTENSION).build());
        session.save(Exercise.builder().name(ExercisesName.BACK_SQUAT).build());
        session.save(Exercise.builder().name(ExercisesName.BOX_JUMPS).build());
        session.save(Exercise.builder().name(ExercisesName.CLOSE_GRIP_BENCH_PRESS).build());
        session.save(Exercise.builder().name(ExercisesName.BICYCLE_CRUNCH_SITTING).build());
        session.save(Exercise.builder().name(ExercisesName.LOW_CABLE_CROSSOVER).build());
        session.save(Exercise.builder().name(ExercisesName.HAMMER_CURL).build());
        session.save(Exercise.builder().name(ExercisesName.SKATERS).build());
        session.save(Exercise.builder().name(ExercisesName.LIFTING_DUMBBELLS_IN_FRONT_OF_YOU).build());
        session.save(Exercise.builder().name(ExercisesName.CABLE_PUSH_DOWN).build());
        session.save(Exercise.builder().name(ExercisesName.REVERSE_CRUNCH).build());
        session.save(Exercise.builder().name(ExercisesName.BULGARIAN_SPLIT_SQUAT).build());
    }

    private Exercise findExerciseByName(String name, Session session){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Exercise> criteria = cb.createQuery(Exercise.class);
        Root<Exercise> root = criteria.from(Exercise.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Exercise_.name), name)
                );
        return session.createQuery(criteria).getSingleResult();
    }
}
