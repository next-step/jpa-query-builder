# 3단계 - QueryBuilder DML

```java

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

}
```

- [X] 요구사항 1 - 위의 정보를 바탕으로 insert 구현해보기

- [X] 요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기

- [X] 요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기

- [X] 요구사항 4 - 위의 정보를 바탕으로 delete 쿼리 만들어보기
