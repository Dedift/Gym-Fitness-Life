package by.tms.gymprogect.database.dto;

import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.Subscription;
import by.tms.gymprogect.database.domain.Train.Exercise;
import by.tms.gymprogect.database.domain.Train.PersonalTrainer;
import by.tms.gymprogect.database.domain.Train.TrainingDay;
import by.tms.gymprogect.database.domain.User.Review;
import by.tms.gymprogect.database.domain.User.User;

import lombok.experimental.UtilityClass;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ModelMapper {
    private static final org.modelmapper.ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new org.modelmapper.ModelMapper();
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        MODEL_MAPPER.createTypeMap(TrainingDay.class, TrainingDayDTO.class)
                .addMapping(TrainingDay::getExercises,TrainingDayDTO::setExercisesDTO)
                .addMapping(TrainingDay::getUser,TrainingDayDTO::setUserDTO);
        MODEL_MAPPER.createTypeMap(Exercise.class, ExerciseDTO.class)
                .addMapping(Exercise::getTrainingDays,ExerciseDTO::setTrainingDaysDTO);
        MODEL_MAPPER.createTypeMap(Order.class, OrderDTO.class)
                .addMapping(Order::getSubscription,OrderDTO::setSubscriptionDTO)
                .addMapping(Order::getUser, OrderDTO::setUserDTO);
        MODEL_MAPPER.createTypeMap(PersonalTrainer.class, PersonalTrainerDTO.class)
                .addMapping(PersonalTrainer::getWards, PersonalTrainerDTO::setWards);
        MODEL_MAPPER.createTypeMap(Review.class, ReviewDTO.class)
                .addMapping(Review::getUser, ReviewDTO::setUserDTO);
        MODEL_MAPPER.createTypeMap(Subscription.class, SubscriptionDTO.class)
                .addMapping(Subscription::getOrder, SubscriptionDTO::setOrderDTO)
                .addMapping(Subscription::getUser, SubscriptionDTO::setUserDTO);
        MODEL_MAPPER.createTypeMap(User.class, UserDTO.class)
                .addMapping(User::getOrders, UserDTO::setOrdersDTO)
                .addMapping(User::getSubscriptions, UserDTO::setSubscriptionsDTO)
                .addMapping(User::getPersonalTrainer, UserDTO::setPersonalTrainerDTO)
                .addMapping(User::getReview, UserDTO::setReviewDTO)
                .addMapping(User::getTrainingProgram, UserDTO::setTrainingProgramDTO);
    }

    public static <D, T> D map(final T entityOrDTO, Class<D> outClass) {
        return MODEL_MAPPER.map(entityOrDTO, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> fromThis, Class<D> toThis) {
        return fromThis.stream()
                .map(entityOrDTO -> map(entityOrDTO, toThis))
                .collect(Collectors.toList());
    }
}
