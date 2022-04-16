package by.tms.gymprogect.database.dto;

import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {
    private static final org.modelmapper.ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new org.modelmapper.ModelMapper();
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
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
