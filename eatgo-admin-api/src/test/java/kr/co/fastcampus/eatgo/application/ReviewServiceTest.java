package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
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
    public void addReview() {
        Restaurant restaurant = Restaurant.builder()
                .name("Bob Zip")
                .address("Seoul")
                .build();

        Review review = Review.builder()
                .name("JOKER")
                .score(3)
                .description("mat it da")
                .build();

        reviewService.addReview(restaurant.getId(), review);

        verify(reviewRepository).save(any());
    }

}