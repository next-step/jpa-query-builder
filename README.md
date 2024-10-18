# jpa-query-builder

## 요구사항
### 1단계 - Reflection
+ Car 객체 정보 가져오기
+ Car 메소드 정보 가져오기
+ Car Test메소드 실행하기
+ @PrintView 애노테이션을 가진 메소드 가져오기
+ @PrintView 애노테이션 메소드 실행
+ private field에 값 할당
+ 인자를 가진 생성자의 인스턴스 생성

### 2단계 - QueryBuilder DDL
+ CREATE 쿼리 문자열 생성하기
+ Entity 어노테이션 확인하기
+ Id 어노테이션 확인하기
+ Column 어노테이션 정보 가져오기
+ String, Integer에 따른 컬럼데이터타입 가져오기
+ Table 어노테이션 정보 가져오기
+ GeneratedValue 어노테이션 정보 가져오기
+ Transient 어노테이션 정보 가져오기

### 3단계 - QueryBuilder DML
+ Insert 쿼리 문자열 생성하기
+ FindAll 쿼리 문자열 생성하기
+ FindAll 호출 시 List로 응답하기
+ FindById 쿼리 문자열 생성하기
+ FindById 호출 시 단일 객체로 응답하기
+ Delete 쿼리 문자열 생성하기
+ Person 데이터를 전부 가져온다.
+ Person 1L 데이터를 가져온다.
+ Person 1L 데이터를 삭제한다.
+ Person 1L 데이터를 가져온다.
+ Person 데이터를 전체 가져온다.

### 3단계 - QueryBuilder DML
+ find를 이용하여 단일 데이터를 select한다.
+ persist를 이용하여 데이터를 insert한다.
+ remove를 이용하여 데이터를 delete한다.
+ update를 이용하여 데이터를 수정한다.