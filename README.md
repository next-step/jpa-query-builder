# jpa-query-builder

# 0단계 - TDD 실습
- [x] 숫자를 문자열로 변경하는 테스트를 만든다.



# 1단계 - Reflection

## 요구사항 1 - 클래스 정보 출력

- [x] 모든 필드 정보를 출력한다.
- [x] 생성자 정보를 출력한다.
- [x] 메서드에 대한 정보를 출력한다.

## 요구사항 2 - test로 시작하는 메서드 실행
- [x] test로 시작하는 메서드를 실행한다.

## 요구사항 3 - @PrintView 애노테이션 메서드 실행
- [x] @PrintView 애노테이션이 있는 메서드를 실행한다.

## 요구사항 4 - private field에 값 할당
- [x] private field name 에 값을 할당할 수 있다.
- [x] private field price 에 값을 할당할 수 있다.

## 요구사항 5 - 인자를 가진 생성자의 인스턴스 생성
- [x] 인자를 가진 생성자를 통해 인스턴스를 생성할 수 있다.


# 2단계 - QueryBuilder DDL

## 요구사항 1 - create 쿼리 만들기

- [x] 하나의 필드에 대해서 컬럼 쿼리를 만들 수 있다.
- [x] 컬럼 쿼리들을 묶어서 하나로 만들 수 있다.
- [x] 생성 쿼리를 만들 수 있다.

## 요구사항 2 - 추가된 정보로 create 쿼리 만들기
- [x] GeneratedValue를 통해 생성 전략을 생성할 수 있다.
- [x] Column을 통해 테이블 이름을 지정할 수 있다.
- [x] Column을 통해 nullable 설정을 할 수 있다.

## 요구사항 3 - 추가된 정보로 create 쿼리 만들기 2
- [x] Table 이름을 변경할 수 있다.
- [x] Transient 를 통해 쿼리를 만들지 않을 수 있다.

## 요구사항 4 - drop 쿼리 만들어 보기
- [ ] drop 쿼리를 만들 수 있다.


# 3단계 - QueryBuilder DML

## 요구사항 1 - insert 구현해보기
- [x] id의 생성 전략이 Identity 일 경우 쿼리문에 포함시키지 않는다.
- [x] transient 컬럼은 포함시키지 않는다.
- [x] not null 인데, null 일 경우 예외를 던진다.
- [x] insert 쿼리를 만들 수 있다.

## 요구사항 2 - findAll 구현해보기
- [x] findAll 쿼리를 만들 수 있다.

## 요구사항 3 - findById 구현해보기
- [x] findById 쿼리를 만들 수 있다.


