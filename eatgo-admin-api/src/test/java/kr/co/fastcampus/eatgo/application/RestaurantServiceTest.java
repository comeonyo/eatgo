package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    public RestaurantServiceTest() {
        MockitoAnnotations.openMocks(this);

        mockRestaurantRepository();

        restaurantService = new RestaurantService(restaurantRepository);
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

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);
        Assertions.assertEquals(restaurant.getId(), 1004L);
    }

    @Test
    public void getRestaurantWithExist() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        Assertions.assertEquals(restaurant.getId(), 1004L);
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