package persistence.sql.fixture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

  @Transient
  private Integer index;

  public PersonFixtureStep3(String name, Integer age, String email) {
    this.name = name;
    this.age = age;
    this.email = email;
  }

  public PersonFixtureStep3(Long id, String name, Integer age, String email) {
    this.name = name;
    this.age = age;
    this.email = email;
  }
  public PersonFixtureStep3() {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonFixtureStep3 that = (PersonFixtureStep3) o;
    return Objects.equals(name, that.name) && Objects.equals(age, that.age)
        && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age, email);
  }
}