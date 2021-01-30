package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ReviewServiceTest {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    public ReviewServiceTest() {
        MockitoAnnotations.openMocks(this);

        this.reviewService = new ReviewService(reviewRepository);
    }

    @Test
    public void getReviews() {
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(Review.builder().description("Cool!").build());

        given(reviewRepository.findAll()).willReturn(mockReviews);

        List<Review> reviews = reviewService.getReviews();

        Review review = reviews.get(0);

        Assertions.assertEquals(review.getDescription(), "Cool!");
    }
}