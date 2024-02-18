package persistence.domain;


@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

}