package persistence.sql.fixture;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class PersonFixture {
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
}
