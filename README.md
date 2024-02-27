# jpa-query-builder


## 실습 환경

- [x] JDK 11

## 1단계 요구사항

- [x] Car 클래스의 정보를 출력
  - [x] Car 클래스의 모든 필드를 출력한다.
  - [x] Car 클래스의 모든 생성자를 출력한다.
  - [x] Car 클래스의 모든 메소드를 출력 한다.

- [x] test로 시작하는 메소드 실행
- [x] @PrintView 어노테이션 메소드 실행
- [x] private field에 값 할당
- [x] 인자를 가진 생성자의 인스턴스 생성

## 2단계 요구사항

- [x] 아래 정보를 바탕으로 create 쿼리 만들어보기
  - [x] Entity 어노테이션이 있는 class와 매핑되는 테이블을 만든다.

- [x] 추가된 정보를 통해 create 쿼리 만들어보기
  - [x] @Column으로 컬럼 이름을 지정할 수 있다.  

- [x] 추가된 정보를 통해 create 쿼리 만들어보기2
  - [x] @Table으로 테이블 이름을 지정할 수 있다.  
  - [x] @Transient은 추가하지 않기
  - [x] @GeneratedValue strategy 값이 있으면 AUTO_INCREMENT 넣기

- [x] 정보를 바탕으로 drop 쿼리 만들어보기

## 3단계 요구사항

- [x] insert 구현해보기
- [x] 모두 조회(findAll) 기능 구현해보기
- [x] 단건 조회(findById) 기능 구현해보기
- [x] delete 쿼리 만들어보기

## 4단계 요구사항

- [x] EntityManager find 구현
- [x] EntityManager persist (insert) 구현
- [x] EntityMaanger remove (delete) 구현

