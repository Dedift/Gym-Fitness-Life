package by.tms.gymprogect.database.dao.impl;

import by.tms.gymprogect.database.config.DbConfigTest;
import by.tms.gymprogect.database.domain.Number;
import by.tms.gymprogect.database.domain.User.Review;
import by.tms.gymprogect.database.util.DatabaseHelper;
import by.tms.gymprogect.database.domain.User.Review_;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DbConfigTest.class)
@Transactional
class ReviewDaoImplTest {

    private static final String I_LIKE_THIS_GYM = "I like this gym!";
    private static final String THIS_GYM_IS_WONDERFUL = "This gym is wonderful!";
    @Autowired
    private ReviewDaoImpl reviewDao;
    @Autowired
    private DatabaseHelper databaseHelper;
    @Autowired
    SessionFactory sessionFactory;

    @BeforeEach
    @Transactional
    void init(){
        databaseHelper.cleanDatabase(sessionFactory.getCurrentSession());
        databaseHelper.prepareData(sessionFactory.getCurrentSession());
    }

    @Test
    void save() {
        Integer id = reviewDao.save(Review.builder()
                .text(I_LIKE_THIS_GYM)
                .build());
        Assertions.assertNotNull(id);
    }

    @Test
    void findAll() {
        List<Review> reviews = reviewDao.findAll();
        Assertions.assertEquals(Number.THREE, reviews.size());
    }

    @Test
    void findById() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Review> criteria = cb.createQuery(Review.class);
        Root<Review> root = criteria.from(Review.class);
        criteria
                .select(root)
                .where(
                        cb.equal(root.get(Review_.text), THIS_GYM_IS_WONDERFUL)
                );
        Optional<Review> maybeReviewByName = Optional.ofNullable(session.createQuery(criteria).getSingleResult());
        Assertions.assertTrue(maybeReviewByName.isPresent());
        Review reviewByText = maybeReviewByName.get();
        Optional<Review> maybeReviewById = reviewDao.findById(reviewByText.getId());
        if (maybeReviewById.isPresent()) {
            Review reviewById = maybeReviewById.get();
            Assertions.assertEquals(reviewByText.getText(), reviewById.getText());
        }
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        Review anyReview = Review.builder()
                .text(THIS_GYM_IS_WONDERFUL)
                .build();
        Integer id = (Integer) session.save(anyReview);
        anyReview.setText(I_LIKE_THIS_GYM);
        reviewDao.update(anyReview);
        Optional<Review> maybeReview = Optional.ofNullable(session.find(Review.class, id));
        Assertions.assertTrue(maybeReview.isPresent());
        Review updatedReview = maybeReview.get();
        Assertions.assertEquals(updatedReview.getText(), anyReview.getText());
    }

    @Test
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        Review anyReview = Review.builder()
                .text(THIS_GYM_IS_WONDERFUL)
                .build();
        Integer id = (Integer) session.save(anyReview);
        reviewDao.delete(anyReview);
        Optional<Review> maybeReview = Optional.ofNullable(session.find(Review.class, id));
        Assertions.assertFalse(maybeReview.isPresent());
    }
}