package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String name;
}
