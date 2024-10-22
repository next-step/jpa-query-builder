# jpa-query-builder
## 1단계 - Reflection
### 요구사항 1 - 클래스 정보 출력
- src/test/java/persistence/study > Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.

### 요구사항 2 - test로 시작하는 메소드 실행
- src/test/java/persistence/study > Car 객체의 메소드 중 test로 시작하는 메소드를 자동으로 실행한다. 이와 같이 Car 클래스에서 test로 시작하는 메소드만 Java Reflection을 활용해 실행.
  - 구현은 src/test/java/persistence/study > ReflectionsTest 클래스의 testMethodRun() 메소드에 한다.

### 요구사항 3 - @PrintView 애노테이션 메소드 실행
- @PrintView애노테이션이 설정되어 있는 메소드를 자동으로 실행한다. 이와 같이 Car 클래스에서 @PrintView 애노테이션으로 설정되어 있는 메소드만 Java Reflection을 활용해 실행하도록 구현한다.
  구현은 src/test/java/persistence/study > ReflectionTest 클래스의 testAnnotationMethodRun() 메소드에 한다.
```java
public class Car {

    ...

    @PrintView
    public void printView() {
        System.out.println("자동차 정보를 출력 합니다.");
    }
    ...
}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintView {
}
```

### 요구사항 4 - private field에 값 할당
- 자바 Reflection API를 활용해 다음 Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
  구현은 src/test/java/persistence/study > ReflectionTest 클래스의 privateFieldAccess() 메소드에 한다.
```java
public class Car {

    private String name;
    private int price;

    public Car() {

    }

    public Car(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    ...
}

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    
    @Test
    public void privateFieldAccess() {
        Class<Car> clazz = Car.class;
        logger.debug(clazz.getName());

    }
}
```

### 요구사항 5 - 인자를 가진 생성자의 인스턴스 생성
- Car 클래스의 인스턴스를 자바 Reflection API를 활용해 Car 인스턴스를 생성한다.
  구현은 src/test/java/persistence/study > ReflectionTest 클래스의 constructorWithArgs() 메소드에 한다.
```java
@Test
void constructorWithArgs() {
    Class<Car> clazz = Car.class;
    logger.debug(clazz.getName());
}

```
-----------------

## 2단계 - QueryBuilder DDL
### 요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다.

##### 정보
```java
@Entity
public class Person {
    
    @Id
    private Long id;
    
    private String name;
    
    private Integer age;
    
}
```

### 요구사항 2 - 추가된 정보를 통해 create 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다.</br>
> 아래의 정보를 통해 Person 클래스의 정보를 업데이트 해준다.

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

### 요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다. </br>
아래의 정보를 통해 Person 클래스의 정보를 업데이트 해준다.

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

### 요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다. </br>
@Entity, @Table를 고려해서 잘 작성해보자.

-------------

## 3단계 - QueryBuilder DML
### 요구사항 1 - Person 클래스 정보를 바탕으로 insert 구현해보기
> 구현은 src/main/java/persistence > sql/dml > 하위에 구현한다. </br>
> 위의 정보를 통해 Person 클래스의 정보를 업데이트 한다.
> @Entity, @Table, @Id, @Column, @Transient 고려하기


columns 과 values를 나누어서 구현해보자. ( insert into table(column1, column2, column3) values (value1, value2, value3))
```java
private String columnsClause(Class<?> clazz) {
         ...
     }
 
     private String valueClause(Object object) {
        ... 
     }
```

### 요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기
> 구현은 src/main/java/persistence > sql/dml > 하위에 구현한다. </br>
> 쿼리 실행을 통해 데이터를 여러 row 를 넣어 정상적으로 나오는지 확인해보자.
```java
public interface Database {
    ... 
    ResultSet executeQuery(String sql);
}
```

### 요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기
> 구현은 src/main/java/persistence > sql/dml 하위에 구현한다.
```java
private String whereClause(String selectQuery, Class<?> clazz) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(selectQuery);
    stringBuilder.append(" where ");
    ...
}
```

### 요구사항 4 - 위의 정보를 바탕으로 delete 쿼리 만들어보기.
> 구현은 src/main/java/persistence > sql/dml > 하위에 구현한다. </br>
@Entity, @Table, @Id, @Column, @Transient 를 고려해서 잘 작성해보자.

