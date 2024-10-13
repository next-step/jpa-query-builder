# jpa-query-builder

## 1단계 Reflection

### 요구사항

- [X] Car class의 모든 클래스 정보를 출력한다
  - [X] Car Class 이름 출력
  - [X] 선언된 필드 출력
  - [X] 선언된 메소드 출력
  - [X] 선언된 생성자 출력
- [X] test로 시작하는 메소드 실행
- [X] @PrintView 애노테이션 메소드 실행
- [X] private field에 값 할당
- [X] 인자를 가진 생정자의 인스턴스 생성

## 2단계 QueryBuilderDDL

### 요구사항

- [X] @Entity와 @Id가 사용된 Person class의 create query 생성
- [X] @GeneratedValue, @Column을 추가하여 Person class 의 create query 생성
- [] @Table, @Transient을 추가하여 Person class의 create query 생성
- [] drop query 생성
