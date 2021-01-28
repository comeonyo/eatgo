package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RestaurantTest {

    @Test
    public void creation() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        Assertions.assertEquals(restaurant.getId(), 1004L);
        Assertions.assertEquals(restaurant.getName(), "Bob Zip");
        Assertions.assertEquals(restaurant.getAddress(), "Seoul");
    }

    @Test
    public void information() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        Assertions.assertEquals(restaurant.getInformation(), "Bob Zip in Seoul");
    }
}