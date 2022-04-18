package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dto.ReviewDTO;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    @Transactional
    Integer save(ReviewDTO reviewDTO);

    List<ReviewDTO> findAll();

    Optional<ReviewDTO> findById(Integer id);

    @Transactional
    void update(ReviewDTO reviewDTO);

    @Transactional
    void delete(ReviewDTO reviewDTO);
}
