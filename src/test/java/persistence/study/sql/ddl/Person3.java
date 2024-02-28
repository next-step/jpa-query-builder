package persistence.study.sql.ddl;

import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "users")
@Entity
public class Person3 {

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

    protected Person3() {

    }

    public Person3(String name, Integer age, String email) {
        this(null, name, age, email);
    }

    public Person3(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Person3 person3 = (Person3) object;

        if (!Objects.equals(id, person3.id)) return false;
        if (!Objects.equals(name, person3.name)) return false;
        if (!Objects.equals(age, person3.age)) return false;
        if (!Objects.equals(email, person3.email)) return false;
        return Objects.equals(index, person3.index);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (index != null ? index.hashCode() : 0);
        return result;
    }
}
