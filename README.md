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
- [x] strategy = GenerationType.IDENTITY 를 이용한 PK statement 조정
- [x] @Column(name) 을 이용한 column 이름 정보 조정
- [x] @Column(nullable) 를 이용한 not null 제약조건 추가


- 요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2
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
- [x] @Table(name) 을 통한 테이블명 조정
- [x] @Transient 을 이용한 column 맵핑 무시


- 요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기
- [x] @Table(name) 을 고려해 drop 쿼리 작성

### 2단계 - QueryBuilder DML
Person 객체 정보
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

- 요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기

columns 과 values 를 나누어서 구현해보자
insert into table (column1, column2, column3) values (value1, value2, value3)
- [ ] Person EntityMetadata 를 이용해 만들어질 columnsClause 구성
- [ ] Person 인스턴스를 이용해 valueClause 구성
- [ ] 두 Clause 를 알맞게 연결

- 요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기
- 요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기
- 요구사항 4 - 위의 정보를 바탕으로 delete 쿼리 만들어보기