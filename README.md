# jpa-query-builder

## Step 1 - Reflection
### 요구 사항
+ [x] Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
+ [x] test로 시작하는 메소드 실행
+ [x] @PrintView 애노테이션 메소드 실행
+ [x] private field에 값 할당
+ [x] 자바 Reflection API를 활용해 Car 인스턴스를 생성한다.

## Step 2 - QueryBuilder DDL
### 요구 사항
+ [x] Gradle 라이브러리 버전 변경 (H2, logback)
+ [x] Person 클래스 정보를 바탕으로 create 쿼리를 만들어본다.
+ [x] Person 클래스에 추가 된 정보를 통해 Create 쿼리를 만들어본다 
  + @GeneratedValue, @Column(name), @Column(nullable) 등 추가
+ [x] Person 클래스에 추가 된 정보를 통해 Create 쿼리를 만들어본다 2
  + @Table(name), @Transient 등 추가 
+ [ ] Person 클래스에 추가 된 정보를 통해 Drop 쿼리를 만들어본다.