# 1단계 - Reflection

## 요구사항 1 - 클래스 정보 출력

- [X] Car 클래스의 모든 필드에 대한 정보를 출력한다.
- [X] Car 클래스의 모든 생성자에 대한 정보를 출력한다.
- [X] Car 클래스의 모든 메소드에 대한 정보를 출력한다.

## 요구사항 2 - test 로 시작하는 메소드 실행

- [X] Car 객체의 메소드 중 `test`로 시작하는 메소드만 Java Reflection 을 활용해 실행하도록 구현한다.
- [X] ReflectionTest 클래스의 testMethodRun() 메소드에 구현한다.

## 요구사항 3 - @PrintView 애노테이션 메소드 실행

- [X] Car 클래스에서 @PrintView 애노테이션으로 설정되어 있는 메소드만 Java Reflection 을 활용해 실행하도록 구현한다.
- [X] ReflectionTest 클래스의 testAnnotationMethodRun() 메소드에 구현한다.

## 요구사항 4 - private field 에 값 할당

- [X] 자바 Reflection API 를 활용해 다음 Car 클래스의 name 과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
- [X] ReflectionTest 클래스의 privateFieldAccess() 메소드에 구현한다.

## 요구사항 5 - 인자를 가진 생성자의 인스턴스 생성

- [X] Car 클래스의 인스턴스를 자바 Reflection API 를 활용해 Car 인스턴스를 생성한다.
- [X] ReflectionTest 클래스의 constructorWithArgs() 메소드에 구현한다.
