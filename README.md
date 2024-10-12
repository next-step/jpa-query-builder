# jpa-query-builder

## 2단계 - QueryBuilder DDL
## 요구사항
### 1. 아래 정보를 바탕으로 create 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다
```java
@Entity
public class Person {
    
    @Id
    private Long id;
    
    private String name;
    
    private Integer age;
    
}
```

### 2. 추가된 정보를 통해 create 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다   
> 아래의 정보를 통해 Person 클래스의 정보를 업데이트 해준다
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

### 3. 추가된 정보를 통해 create 쿼리 만들어보기2
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다   
> 아래의 정보를 통해 Person 클래스의 정보를 업데이트 해준다
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

### 4. 정보를 바탕으로 drop 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다
> @Entity, @Table를 고려해서 잘 작성해보자