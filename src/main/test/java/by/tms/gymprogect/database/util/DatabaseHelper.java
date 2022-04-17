package by.tms.gymprogect.database.util;

import by.tms.gymprogect.database.domain.Number;
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

    private static final String SVETAS_MAIL = "Popova@gmail.com";
    private static final String SVETAS_BANK_CARD = "234214324";
    private static final String SVETA = "Sveta";
    private static final String POPOVA = "Popova";
    private static final String PASSWORD = "pass";
    private static final String SVETAS_PHONE_NUMBER = "+375294432576";
    private static final String SVETAS_REVIEW = "I'm very happy";
    private static final String PAVELS_MAIL = "Pavel@mail.ru";
    private static final String PAVELS_BANK_CARD = "87396729";
    private static final String PAVEL = "Pavel";
    private static final String LETOV = "Letov";
    private static final String PAVELS_PHONE_NUMBER = "+375448975632";
    private static final String PAVELS_REVIEW = "This is the best gym!";
    private static final String NIKITAS_MAIL = "nikitaKlimovich@mail.ru";
    private static final String NIKITAS_BANK_CARD = "87396729";
    private static final String NIKITA = "Nikita";
    private static final String KLIMOVICH = "Klimovich";
    private static final String NIKITAS_PHONE_NUMBER = "+375259963298";
    private static final String NIKITAS_REVIEW = "This gym is wonderful!";
    private static final String OLGA = "Olga";
    private static final String NOVIKOVA = "Novikova";
    private static final String OLEG = "Oleg";
    private static final String PRISTAVKO = "Pristavko";

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
        User svetaPopova = User.builder()
                .email(SVETAS_MAIL)
                .password(PASSWORD)
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(Number.TWO_THOUSAND, Number.FOUR, Number.TWELVE))
                .userData(UserData.builder()
                        .bankCard(SVETAS_BANK_CARD)
                        .firstName(SVETA)
                        .lastName(POPOVA)
                        .phoneNumber(SVETAS_PHONE_NUMBER)
                        .build())
                .role(Role.USER)
                .review(Review.builder()
                        .text(SVETAS_REVIEW)
                        .build())
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
                        .countSetsPerExercise(Number.THREE)
                        .countRepetitionsPerSet(Number.TWELVE)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.BACK_SQUAT, session),
                                findExerciseByName(ExercisesName.CABLE_PUSH_DOWN, session),
                                findExerciseByName(ExercisesName.REVERSE_CRUNCH, session)))
                        .countSetsPerExercise(Number.THREE)
                        .countRepetitionsPerSet(Number.TWELVE)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.BOX_JUMPS, session),
                                findExerciseByName(ExercisesName.CLOSE_GRIP_BENCH_PRESS, session),
                                findExerciseByName(ExercisesName.BICYCLE_CRUNCH_SITTING, session)))
                        .countSetsPerExercise(Number.THREE)
                        .countRepetitionsPerSet(Number.TWELVE)
                        .build()));
        User pavelLetov = User.builder()
                .email(PAVELS_MAIL)
                .password(PASSWORD)
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(Number.TWO_THOUSAND_TWO, Number.SIX, Number.THREE))
                .userData(UserData.builder()
                        .bankCard(PAVELS_BANK_CARD)
                        .firstName(PAVEL)
                        .lastName(LETOV)
                        .phoneNumber(PAVELS_PHONE_NUMBER)
                        .build())
                .role(Role.USER)
                .review(Review.builder()
                        .text(PAVELS_REVIEW)
                        .build())
                .build();
        Order pavelsOrder = Order.builder()
                .countTrain(Number.THIRTY_SIX)
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
                        .countSetsPerExercise(Number.FOUR)
                        .countRepetitionsPerSet(Number.TWENTY)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.LIFTING_DUMBBELLS_IN_FRONT_OF_YOU, session),
                                findExerciseByName(ExercisesName.CABLE_PUSH_DOWN, session),
                                findExerciseByName(ExercisesName.REVERSE_CRUNCH, session)))
                        .countSetsPerExercise(Number.FOUR)
                        .countRepetitionsPerSet(Number.TWENTY)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.BOX_JUMPS, session),
                                findExerciseByName(ExercisesName.CLOSE_GRIP_BENCH_PRESS, session),
                                findExerciseByName(ExercisesName.BICYCLE_CRUNCH_SITTING, session)))
                        .countSetsPerExercise(Number.FOUR)
                        .countRepetitionsPerSet(Number.TWENTY)
                        .build()));
        User nikitaKlimovich = User.builder()
                .email(NIKITAS_MAIL)
                .password(PASSWORD)
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(Number.TWO_THOUSAND, Number.FOUR, Number.EIGHT))
                .userData(UserData.builder()
                        .bankCard(NIKITAS_BANK_CARD)
                        .firstName(NIKITA)
                        .lastName(KLIMOVICH)
                        .phoneNumber(NIKITAS_PHONE_NUMBER)
                        .build())
                .role(Role.USER)
                .review(Review.builder()
                        .text(NIKITAS_REVIEW)
                        .build())
                .build();
        Order niksOrder = Order.builder()
                .countTrain(Number.TWELVE)
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
                        .countSetsPerExercise(Number.FOUR)
                        .countRepetitionsPerSet(Number.FIFTEEN)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.LIFTING_DUMBBELLS_IN_FRONT_OF_YOU, session),
                                findExerciseByName(ExercisesName.CABLE_PUSH_DOWN, session),
                                findExerciseByName(ExercisesName.REVERSE_CRUNCH, session)))
                        .countSetsPerExercise(Number.FOUR)
                        .countRepetitionsPerSet(Number.FIFTEEN)
                        .build(),
                TrainingDay.builder()
                        .exercises(List.of(
                                findExerciseByName(ExercisesName.BOX_JUMPS, session),
                                findExerciseByName(ExercisesName.CLOSE_GRIP_BENCH_PRESS, session),
                                findExerciseByName(ExercisesName.BICYCLE_CRUNCH_SITTING, session)))
                        .countSetsPerExercise(Number.FOUR)
                        .countRepetitionsPerSet(Number.NINE)
                        .build()));
        PersonalTrainer olgaNovikova = PersonalTrainer.builder()
                .firstName(OLGA)
                .lastName(NOVIKOVA)
                .experience(Number.FOUR)
                .gender(Gender.FEMALE)
                .build();
        olgaNovikova.getWards().add(svetaPopova);
        olgaNovikova.getWards().add(pavelLetov);
        session.save(olgaNovikova);
        PersonalTrainer olegPristavko = PersonalTrainer.builder()
                .firstName(OLEG)
                .lastName(PRISTAVKO)
                .experience(Number.TWO)
                .gender(Gender.MALE)
                .build();
        olegPristavko.getWards().add(nikitaKlimovich);
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
