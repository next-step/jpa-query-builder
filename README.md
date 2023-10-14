# jpa-query-builder

## SQL 쿼리 빌더 구현

### 2단계 - QueryBuilder DDL

- 요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기
```java
@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
```
- [x] Person 클래스를 순회하며 필드 정보를 가져온다.
- [x] 필드 정보를 바탕으로 ddl - create query 를 만든다.
- [x] @Id 가 있는 필드는 pk 로 만든다.


- 요구사항 2 - 추가된 정보를 통해 create 쿼리 만들어보기
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
- [ ] strategy = GenerationType.IDENTITY 를 이용한 PK statement 조정
- [ ] @Column(name) 을 이용한 column 이름 정보 조정
- [ ] @Column(nullable) 를 이용한 not null 제약조건 추가


- 요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2
- 요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기
