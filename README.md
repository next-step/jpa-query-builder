# jpa-query-builder

## 2단계 - QueryBuilder DDL

### 요구사항 1
```java
@Entity
public class Person {
    
    @Id
    private Long id;
    
    private String name;
    
    private Integer age;
    
}
```
- @Entity 어노테이션이 달린 클래스를 가지고 create 쿼리를 만들 수 있다.
- 필드 타입과 명을 받아 추가할 수 있다.
- @Id 어노테이션이 붙는 경우 primary key로 지정할 수 있다.

### 요구사항 2
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
- @Column 어노테이션이 붙는 경우 옵션에 따라 쿼리문이 수정된다.
  - name 옵션이 있는 경우 해당 column의 이름은 해당값으로 지정된다.
  - nullable 옵션이 있는 경우 해당 column의 nullable 여부는 해당값으로 지정된다.
- @GeneratedValue 어노테이션이 붙는 경우 옵션에 따라 쿼리문이 수정된다.
  - IDENTITY 타입이 입력될 경우, auto increment로 id가 생성된다.
