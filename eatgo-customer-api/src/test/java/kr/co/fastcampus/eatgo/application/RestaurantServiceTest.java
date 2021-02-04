package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    public RestaurantServiceTest() {
        MockitoAnnotations.openMocks(this);

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();

        restaurantService = new RestaurantService(
                restaurantRepository, menuItemRepository, reviewRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        restaurants.add(restaurant);
        restaurant.setMenuItems(Arrays.asList(MenuItem.builder().name("Kimchi").build()));

        given(restaurantRepository
                .findAllByAddressContainingAndCategoryId("Seoul", 1L))
                .willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = MenuItem.builder().name("Kimchi").build();
        menuItems.add(menuItem);

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        Review review = Review.builder()
                .name("JOKER")
                .score(4)
                .description("great")
                .build();
        reviews.add(review);

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    @Test
    public void getRestaurants() {
        String region = "Seoul";
        Long categoryId = 1L;
        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);

        Restaurant restaurant = restaurants.get(0);
        Assertions.assertEquals(restaurant.getId(), 1004L);
    }

    @Test
    public void getRestaurantWithExist() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));

        Assertions.assertEquals(restaurant.getId(), 1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        Assertions.assertEquals(menuItem.getName(), "Kimchi");

        Review review = restaurant.getReviews().get(0);
        Assertions.assertEquals(review.getName(), "JOKER");
    }

    @Test
    public void getRestaurantWithNotExist() {
        Assertions.assertThrows(RestaurantNotFoundException.class,
                () -> restaurantService.getRestaurant(40404L));
    }

    @Test
    public void addRestaurant() {
        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong").address("Seoul").build();

        Restaurant created = restaurantService.addRestaurants(restaurant);

        Assertions.assertEquals(created.getId(), 1234L);
    }

    @Test
    public void updateRestaurant() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L).name("Bob Zip").address("Seoul").build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));


        restaurantService.updateRestaureant(1004L, "Sool Zip", "Busan");

        Assertions.assertEquals("Sool Zip", restaurant.getName());
        Assertions.assertEquals("Busan", restaurant.getAddress());
    }
}