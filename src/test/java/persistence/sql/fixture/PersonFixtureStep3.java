package persistence.sql.fixture;

import jakarta.persistence.*;

import java.util.Objects;
@Table(name = "users")
@Entity
public class PersonFixtureStep3 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nick_name")
  private String name;

  @Column(name = "old")
  private Integer age;

  @Column(nullable = false)
  private String email;

  @Override
  public String toString() {
    return "PersonFixtureStep3{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", email='" + email + '\'' +
            '}';
  }

  @Transient
  private Integer index;

  public PersonFixtureStep3(String name, Integer age, String email) {
    this.name = name;
    this.age = age;
    this.email = email;
  }

  public PersonFixtureStep3(Long id, String name, Integer age, String email) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.email = email;
  }

  public PersonFixtureStep3() {

  }

  public String getEmail() {
    return email;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonFixtureStep3 that = (PersonFixtureStep3) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(age, that.age) && Objects.equals(email, that.email) && Objects.equals(index, that.index);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, age, email);
  }
}
