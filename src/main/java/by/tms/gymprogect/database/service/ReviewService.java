package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.ReviewDao;
import by.tms.gymprogect.database.domain.User.Review;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.ReviewDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private ReviewDao reviewDao;

    @Autowired
    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Transactional
    public Integer save(ReviewDTO reviewDTO) {
        Review review = ModelMapper.map(reviewDTO, Review.class);
        return reviewDao.save(review);
    }

    public List<ReviewDTO> findAll() {
        List<Review> review = reviewDao.findAll();
        return ModelMapper.mapAll(review, ReviewDTO.class);
    }

    public Optional<ReviewDTO> findById(Integer id) {
        Optional<Review> maybeReview = reviewDao.findById(id);
        ReviewDTO reviewDTO = ReviewDTO.builder().build();
        if (maybeReview.isPresent()) {
            Review review = maybeReview.get();
            reviewDTO = ModelMapper.map(review, ReviewDTO.class);
        }
        return Optional.ofNullable(reviewDTO);
    }

    @Transactional
    public void update(ReviewDTO reviewDTO) {
        Review review = ModelMapper.map(reviewDTO, Review.class);
        reviewDao.update(review);
    }

    @Transactional
    public void delete(ReviewDTO reviewDTO) {
        Review review = ModelMapper.map(reviewDTO, Review.class);
        reviewDao.delete(review);
    }
}