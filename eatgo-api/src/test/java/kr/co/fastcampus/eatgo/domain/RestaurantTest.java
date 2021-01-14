package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RestaurantTest {

    @Test
    public void creation() {
        Restaurant restaurant = new Restaurant(1004L, "Bob Zip", "Seoul");

        Assertions.assertEquals(restaurant.getId(), 1004L);
        Assertions.assertEquals(restaurant.getName(), "Bob Zip");
        Assertions.assertEquals(restaurant.getAddress(), "Seoul");
    }

    @Test
    public void information() {
        Restaurant restaurant = new Restaurant(1004L, "Bob Zip", "Seoul");

        Assertions.assertEquals(restaurant.getInformation(), "Bob Zip in Seoul");
    }
}