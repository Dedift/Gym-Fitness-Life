package by.tms.gymprogect.database.service;

import by.tms.gymprogect.database.dao.ReviewDao;
import by.tms.gymprogect.database.domain.User.Review;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.ReviewDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private static final String DELETE_REVIEW = "Delete review: {}";
    private static final String ACCEPTED_TO_DELETE = "Accepted to delete reviewDTO: {}";
    private static final String UPDATE_REVIEW = "Update review: {}";
    private static final String ACCEPTED_TO_UPDATE = "Accepted to update reviewDTO: {}";
    private static final String FIND_REVIEW_BY_ID = "Find review: {} by id:{}";
    private static final String FIND_ALL_REVIEWS = "Find all reviews: {}";
    private static final String SAVE_REVIEW_WITH_ID = "Save review: {} with id:{}";
    private static final String ACCEPTED_TO_SAVE = "Accepted to save reviewDTO: {}";
    private ReviewDao reviewDao;
    private Logger logger = LogManager.getLogger(ReviewService.class);

    @Autowired
    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    /**
     * Accept the reviewDTO, map to a review, save, and return its primary key
     */
    @Transactional
    public Integer save(ReviewDTO reviewDTO) {
        logger.debug(ACCEPTED_TO_SAVE, reviewDTO);
        Review review = ModelMapper.map(reviewDTO, Review.class);
        Integer id = reviewDao.save(review);
        logger.debug(SAVE_REVIEW_WITH_ID, review, id);
        return id;
    }

    /**
     * Find all review, map to DTOs, and get
     */
    public List<ReviewDTO> findAll() {
        List<Review> reviews = reviewDao.findAll();
        logger.debug(FIND_ALL_REVIEWS, reviews);
        return ModelMapper.mapAll(reviews, ReviewDTO.class);
    }

    /**
     * Find a review by id, map to DTO, and get
     */
    public Optional<ReviewDTO> findById(Integer id) {
        Optional<Review> maybeReview = reviewDao.findById(id);
        ReviewDTO reviewDTO = ReviewDTO.builder().build();
        if (maybeReview.isPresent()) {
            Review review = maybeReview.get();
            logger.debug(FIND_REVIEW_BY_ID, review, id);
            reviewDTO = ModelMapper.map(review, ReviewDTO.class);
        }
        return Optional.ofNullable(reviewDTO);
    }

    /**
     * Accept the reviewDTO, map to a review, and update
     */
    @Transactional
    public void update(ReviewDTO reviewDTO) {
        logger.debug(ACCEPTED_TO_UPDATE, reviewDTO);
        Review review = ModelMapper.map(reviewDTO, Review.class);
        reviewDao.update(review);
        logger.debug(UPDATE_REVIEW, review);
    }

    /**
     * Accept the reviewDTO, map to a review, and delete
     */
    @Transactional
    public void delete(ReviewDTO reviewDTO) {
        logger.debug(ACCEPTED_TO_DELETE, reviewDTO);
        Review review = ModelMapper.map(reviewDTO, Review.class);
        reviewDao.delete(review);
        logger.debug(DELETE_REVIEW, review);
    }
}
