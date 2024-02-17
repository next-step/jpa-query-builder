# jpa-query-builder

## 🚀 0단계 - TDD 실습

### 요구사항

* [x] 123 이라는 숫자를 문자열로 반환

## 🚀 0단계 - TDD 실습

### 요구사항

* [x] 요구사항 1 - 클래스 정보 출력
    * src/test/java/persistence/study > Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
* [x] 요구사항 2 - test로 시작하는 메소드 실행
    * src/test/java/persistence/study > Car 객체의 메소드 중 test로 시작하는 메소드를 자동으로 실행한다. 이와 같이 Car 클래스에서 test로 시작하는 메소드만 Java Reflection을 활용해 실행하도록 구현한다.
    * 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 testMethodRun() 메소드에 한다.
* [x] 요구사항 3 - @PrintView 애노테이션 메소드 실행
    * @PrintView애노테이션일 설정되어 있는 메소드를 자동으로 실행한다. 이와 같이 Car 클래스에서 @PrintView 애노테이션으로 설정되어 있는 메소드만 Java Reflection을 활용해 실행하도록 구현한다.
    * 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 testAnnotationMethodRun() 메소드에 한다.
* [x] 요구사항 4 - private field에 값 할당
    * 자바 Reflection API를 활용해 다음 Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
    * 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 privateFieldAccess() 메소드에 한다.
* [x] 요구사항 5 - 인자를 가진 생성자의 인스턴스 생성
    * Car 클래스의 인스턴스를 자바 Reflection API를 활용해 Car 인스턴스를 생성한다.
    * 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 constructorWithArgs() 메소드에 한다.

## 🚀 2단계 - QueryBuilder DDL

### 요구사항

* [ ] 요구사항 1 - 아래 정보를 바탕으로 create 쿼리 만들어보기
    * 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다
    * ```java
      @Entity
      public class Person {
      
          @Id
          private Long id;
          
          private String name;
          
          private Integer age;
      
      }
      ```
* [ ] 요구사항 2 - 추가된 정보를 통해 create 쿼리 만들어보기
    * 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다
    * 아래의 정보를 통해 Person 클래스의 정보를 업데이트 해준다
    * ```java
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
* [ ] 요구사항 3 - 추가된 정보를 통해 create 쿼리 만들어보기2
    * 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다
    * 아래의 정보를 통해 Person 클래스의 정보를 업데이트 해준다
    * ```java
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
* [ ] 요구사항 4 - 정보를 바탕으로 drop 쿼리 만들어보기
    * 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다
    * @Entity, @Table를 고려해서 잘 작성해보자