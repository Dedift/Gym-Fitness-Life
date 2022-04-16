package by.tms.gymprogect.database.dao;

import by.tms.gymprogect.database.domain.BaseEntity;

import java.io.Serializable;

import java.util.List;
import java.util.Optional;

public interface BaseDAO <PK extends Serializable, E extends BaseEntity<PK>>{

    Optional<E> findById(PK id);

    PK save(E entity);

    void update(E entity);

    void delete(E entity);

    List<E> findAll();

}
