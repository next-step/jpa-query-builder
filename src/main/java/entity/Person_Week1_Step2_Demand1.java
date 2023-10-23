package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person_Week1_Step2_Demand1 {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
