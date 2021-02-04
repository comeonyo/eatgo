package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.interfaces.RestaurantController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob Zip")
                .address("Seoul").build());

        given(restaurantService.getRestaurants("Seoul", 1L))
                .willReturn(restaurants);

        mvc.perform(get("/restaurants?region=Seoul&category=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob Zip\"")));
    }

    @Test
    public void detailWithExist() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob Zip")
                .address("Seoul")
                .build();
        MenuItem menuItem = MenuItem.builder().name("Kimchi").build();
        restaurant.setMenuItems(Arrays.asList(menuItem));
        Review review = Review.builder()
                .name("JOKER")
                .score(4)
                .description("mat it da")
                .build();
        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob Zip\"")))
                .andExpect(content().string(containsString("Kimchi")))
                .andExpect(content().string(containsString("mat it da")));

    }

    @Test
    public void detailWithNotExist() throws Exception {
        given(restaurantService.getRestaurant(40404L))
                .willThrow(new RestaurantNotFoundException(40404L));

        mvc.perform(get("/restaurants/40404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

}