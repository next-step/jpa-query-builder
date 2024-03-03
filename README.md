# 기능 요구사항

## 요구사항 1
- [x] person 클래스 정보에 맞는 create table 쿼리 생성
  - [x] @Entity 가 붙은 클래스명을 테이블명으로 생성
  - [x] 클래스 필드를 테이블 컬럼으로 생성
  - [x] @Id 가 붙은 클래스 필드는 기본키를 가진 테이블 컬럼으로 생성

## 요구사항 2
- [x] person 클래스 정보에 맞는 create table 쿼리 생성
  - [x] @GeneratedValue 가 붙은 클래스 필드는 auto increment 가 설정된 테이블 컬럼으로 생성
  - [x] @Column 에 name 값이 지정된 클래스 필드는 테이블 컬럼명을 name 값으로 생성
  - [x] @Column 에 nullable 여부가 지정된 클래스 필드는 테이블 컬럼의 nullable 생성

## 요구사항 3
- [x] person 클래스 정보에 맞는 create table 쿼리 생성
    - [x] @Table 에 name 값이 지정된 클래스는 테이블명을 name 값으로 생성 
    - [x] @Transient 가 붙은 클래스 필드는 테이블 컬럼으로 반영하지 않음

## 요구사항 4
- [x] person 클래스의 drop 쿼리 생성
