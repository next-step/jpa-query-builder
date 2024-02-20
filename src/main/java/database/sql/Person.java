package database.sql;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Table(name = "users")
@Entity
public class Person {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nick_name")
    private String name;
    @Column(name = "old")
    private Integer age;
    @Column(nullable = false)
    private String email;
    @Transient
    private Integer index;

    public Person(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Person() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("nick_name", name);
        map.put("old", age);
        map.put("email", email);
        return map;
    }
}
