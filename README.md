# jpa-query-builder

## step1

요구사항

- [x] 클래스 정보 출력
  - Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
- [x] test로 시작하는 메소드 실행
    - Car 객체의 메소드 중 test로 시작하는 메소드를 자동으로 실행한다.
- [x] @PrintView 애노테이션 메소드 실행
    - @PrintView 애노테이션이 설정되어 있는 메소드를 자동으로 실행한다.
- [x] private field에 값 할당
    - Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
- [x] 인자를 가진 생성자의 인스턴스 생성
    - Car 클래스의 인스턴스를 자바 Reflection API를 활용해 Car 인스턴스를 생성한다.

---

## step2

요구사항

- [x] 아래 정보를 바탕으로 create 쿼리 만들어보기
  ```java
    @Entity
    public class Person {
    @Id
    private Long id;
    
    private String name;
    
    private Integer age;
    }
  ```
- [x] 추가된 정보를 통해 create 쿼리 만들어보기
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
- [x] 추가된 정보를 통해 create 쿼리 만들어보기 2
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
- [x] 정보를 바탕으로 drop 쿼리 만들어보기

---

## step3

요구사항

- [x] 아래 정보를 바탕으로 insert 구현해보기
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
- [x] 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기
- [x] 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기
- [x] 위의 정보를 바탕으로 delete 쿼리 만들어보기


---

## step4

요구사항

- [x] EntityManager 인터페이스를 생성 후 find 구현
  ```
  <T> T find(Class<T> clazz, Long Id);
  ```
- [x] EntityManager 인터페이스를 생성 후 persist 구현
  ```
  void persist(Object entity);
  ```
- [x] EntityManager 인터페이스를 생성 후 remove 구현
  ```
  void remove(Object entity);
  ```
- [x] EntityManager 인터페이스를 생성 후 update 구현
