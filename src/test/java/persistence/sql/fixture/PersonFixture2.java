package persistence.sql.fixture;

import jakarta.persistence.*;

@Entity
public class PersonFixture2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "nick_name")
  private String name;
  @Column(name = "old")
  private Integer age;
  @Column(nullable = false)
  private String email;
  @Column
  private Integer index;

}
