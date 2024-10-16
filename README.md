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

- [X] Person Class의 Create Query 생성
  - [X] Person Class에 @Entity가 사용되지 않았으면 Create Query를 할 수 없으므로 오류 출력
  - [X] @Id가 사용된 필드의 경우 해당 컬럼 생성 문에 not null 포함 및 primary key에 추가
  - [X] @GeneratedValue 의 값이 IDENTITY일 때 컬럼 생성 문에 auto_increment 포함
  - [X] @Column name에 따라 컬럼 이름 변경 
  - [X] @Table name에 따라 테이블 이름 변경
  - [X] @Transient 이 포함된 컬럼은 컬럼 생성하지 않음
- [X] Person Class의 Drop Query 생성

## 2단계 QueryBuilderDML

### 요구사항

- [] Person class 의 insert query 생성
- [] Person class 의 findAll query 생성
- [] Person class 의 findById query 생성
- [] Person class 의 delete query 생성
