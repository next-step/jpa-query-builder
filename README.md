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

### [ ] 요구사항 3 - @PrintView 애노테이션 메소드 실행

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

### [ ] 요구사항 4 - private field에 값 할당

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

### [ ] 요구사항 5 - 인자를 가진 생성자의 인스턴스 생성

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



