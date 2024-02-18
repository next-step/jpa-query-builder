package persistence.domain;


@Entity
public class Person {

    @Id
    private Long id;

    private Integer age;
    private String name;

}