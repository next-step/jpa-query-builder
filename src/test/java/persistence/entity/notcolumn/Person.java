package persistence.entity.notcolumn;

import jakarta.persistence.*;

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

    // TODO: (질문) 운영 코드를 성공 시키기 위해 테스트 fixture를 수정하는 것이 과연 좋은 걸까? (고민내용: DtoMapper 개발 위해 본 기본 생성자 추가함.)
    public Person() {
    }

    public Person(String name, int age, String email, int index) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.index = index;
    }
}
