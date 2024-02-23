# jpa-query-builder

## 프로그래밍 요구사항

* 모든 로직에 단위 테스트를 구현한다. 단, 테스트하기 어려운 UI 로직은 제외
* 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분한다.
* UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리한다.
* 자바 코드 컨벤션을 지키면서 프로그래밍한다.
* 이 과정의 Code Style은 intellij idea Code Style. Java을 따른다.
* intellij idea Code Style. Java을 따르려면 code formatting 단축키(Windows : Ctrl + Alt + L. Mac : ⌥ (Option) + ⌘*  (Command) +
  L.)를 사용한다.
* 규칙 : 객체지향설계의 규칙을 다 지켜본다

## 기능 목록 및 commit 로그 요구사항

* 기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
* git의 commit 단위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.
* 참고문서: AngularJS Commit Message Conventions

## 구현할 기능 목록 리스트

- [x] String 클래스의 학습 테스트 작성
    - [x] src>test>java>persistence>study 하위에 StringTest 클래스를 하나 생성한다
    - [x] 요구사항1 : 123 이라는 숫자를 문자열로 반환하는 테스트 코드를 생성한다.

## 🚀 1단계 - Reflection

### Java Reflection 실습

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

    @PrintView
    public void printView() {
        System.out.println("자동차 정보를 출력 합니다.");
    }

    public String testGetName() {
        return "test : " + name;
    }

    public String testGetPrice() {
        return "test : " + price;
    }
}
```

### [x] 요구사항 1 - 클래스 정보 출력

src/test/java/persistence/study > Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.

- [x] 모든 필드 출력
- [x] 모든 생성자 출력
- [x] 모든 메서드 출력

예제 :

```java
public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
    }
}
```

### [x] 요구사항 2 - test로 시작하는 메소드 실행

- [x] Car 클래스에서 test로 시작하는 메소드만 Java Reflection을 활용해 실행하도록 구현
    - [x] Car 객체의 메소드 중 `test로 시작하는 메소드를 자동으로 실행`
    - [x] 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 `testMethodRun()`

> 힌트
>
> Car의 모든 메소드 목록을 구한다(clazz.getDeclaredMethods())
>
> 메소드 이름이 test로 시작하는 경우 method.invoke(clazz.newInstance());
>
> Class가 기본 생성자를 가질 경우 getDeclaredConstructor().newInstance()를 활용해 인스턴스를 생성할 수 있다.

### [x] 요구사항 3 - @PrintView 애노테이션 메소드 실행

* `@PrintView` 애노테이션이 설정되어 있는 메소드를 자동으로 실행한다.
    * = Car 클래스에서 `@PrintView` 애노테이션으로 설정되어 있는 메소드만 Java Reflection을 활용해 실행하도록 구현.
* 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 `testAnnotationMethodRun()` 메소드에 한다.

예제:

```java
public class Car {

    // ...

    @PrintView
    public void printView() {
        System.out.println("자동차 정보를 출력 합니다.");
    }

    // ...
}
```

> 힌트
>
> Method 클래스의 isAnnotationPresent(PrintView.class) 활용

### [x] 요구사항 4 - private field에 값 할당

* 자바 Reflection API를 활용해, Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
* 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 `privateFieldAccess()` 메소드에 한다.

예제:

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
    // ...
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

> 힌트
>
> Class의 getDeclaredField(String name) 메소드를 이용해 private Field를 구한다.
>
> Field.setAccessible(true)로 설정한 후 값을 할당한다.
>
> 이전 단계에서 기본 생성자로 객체를 생성한 후 field.set(car, “소나타”)과 같이 private 필드에 값을 할당할 수 있다.
>
> 최종적으로 Car 인스턴스에 값이 할당되어 있는지 확인한다.

### [x] 요구사항 5 - 인자를 가진 생성자의 인스턴스 생성

* 자바 Reflection API를 활용해 Car 인스턴스를 생성한다.
* 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 `constructorWithArgs()` 메소드에 한다.

예제:

```java

@Test
void constructorWithArgs() {
    Class<Car> clazz = Car.class;
    logger.debug(clazz.getName());
}
```

> 힌트
>
> 기본 생성자가 없는 경우 clazz.newInstance()로 인스턴스 생성할 수 없음.
>
> 인스턴스를 생성하기 위한 Constructor를 먼저 찾아야 함. Class의 getDeclaredConstructors() 활용.
>
> constructor.newInstance(Object... args)로 인스턴스 생성

## Feedback

* Java에서 int.class와 Integer.class는 서로 다른 것으로 취급
    * int.class는 Java의 기본 타입인 int의 Class 객체를 나타냅니다. int.class는 리플렉션과 같은 상황에서 기본 타입 int를 참조할 때 사용.
    * Integer.class는 int의 래퍼 클래스인 Integer의 Class 객체를 나타냅니다.

* 프라이빗 필드 값 할당법
    * 원하는 필드를 리플렉션을 이용해 추출 및 set accessible.
    * 객체 생성후, accessible해진 필드를 이용해 값을 set.
* revise all commit msg
    * `git reasbe -i HEAD^N` and use `r(eword`
* 코드 일부분만 커밋할수 있는 툴 확인
    * Use patch mode `git add -p`

## 🚀 2단계 - QueryBuilder DDL

### [x] 요구사항 1 - create 쿼리 만들어보기

> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다

TODO list :

- [x] `@Entity`가 붙은 class와 매핑되는 테이블을 만들수 있어야 한다.
    - [x] Table Name : 클래스 명
    - [x] Table PK Column Name : `@Id`가 붙은 필드 Name
    - [x] Table PK Column Type : `@Id`가 붙은 필드 Type에 매핑되는 DB Type(예: Long이라면 BIGINT)
    - [x] Table Columns : `@Id`가 붙지 않는 필드들
        - [x] Column Name = 필드 Name
        - [x] Column Type = 필드 Type에 매핑되는 DB Type(예: String이라면 VARCHAR)

Note :

- 최종적으론, Hibernate의 작동방식 흉내내는 것을 목표로 한다.
- package.name이 주어졌을때, 해당 패키지 내의 `@Entity`가 붙은 클래스를 찾아, 해당 클래스의 정보를 바탕으로 create 쿼리를 만들어낸다.

```java

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
```

### [ ] 요구사항 2 - 추가된 정보를 통해 create 쿼리 만들어보기

> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다

TODO List :

- [ ] `@Id`가 붙은 필드에서, `@GeneratedValue`의 전략대로 PK 전략을 지정할 수 있어야 한다.
- [ ] `@Column`이 붙은 필드와 매핑되는 컬럼 이름을 지정할 수 있어야 한다.
    - [ ] name value가 지정되어 있다면 해당 value로 컬럼 이름을 지정해야 한다.
    - [ ] nullable value가 지정되어 있다면 해당 필드 값의 nullable여부를 validate해야 한다.

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

### [ ] 요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2

> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다

- [ ] `@Table`가 붙은 클래스이름과 동일한 테이블을 지정할 수 있어야 한다?
- [ ] `@Transient`가 붙은 필드는 XX해야 한다?

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

### [ ] 요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기

> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다

@Entity, @Table를 고려해서 잘 작성해보자

### 개인적 개선사항

### [ ] 매번 규약을 따르지 않은 커밋메시지를 rebase하는 것보다 사전에 막아보자.

- [ ] try Husky
- [ ] try Commitlint

### [x] 클래스의 마지막 줄에 자동으로 개행문자 추가

Preferences -> Editor -> General -> Ensure line feed at file end on save 체크
