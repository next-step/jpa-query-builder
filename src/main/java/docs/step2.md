# 2단계 - QueryBuilder DDL

- [X] 요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기

```java

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;
}
```

- [X] 요구사항 2 - 추가된 정보를 통해 create 쿼리 만들어보기

```java

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
}
```

- [X] 요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2

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

- [X] 요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기
