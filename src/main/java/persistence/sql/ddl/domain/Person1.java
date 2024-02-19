package persistence.sql.ddl.domain;


@Entity
public class Person1 {

    @Id
    private Long id;
    private Integer age;
    private String name;

}