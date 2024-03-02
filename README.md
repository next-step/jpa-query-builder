# jpa-query-builder 요구사항

## 🚀 1단계 - Reflection

1. Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
2. Car 객체의 메소드 중 test로 시작하는 메소드를 자동으로 실행한다.
3. @PrintView annotation 설정되어 있는 메소드를 자동으로 실행한다.
4. java Reflection API를 활용해 다음 Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
5. Car 클래스의 인스턴스를 java Reflection API를 활용해 Car 인스턴스를 생성한다.

## 🚀 2단계 - QueryBuilder DDL

1. @Entity, @Id 가 포함된 클래스를 이용하여 create 쿼리 만들기
    - @Id 가 붙은 컬럼이 반드시 존재해야한다.
    - 컬럼 타입이 아래처럼 변환 되어야한다.
        - String -> VARCHAR
        - Integer -> INT
        - Long -> BIGINT
        - LocalDate -> DATETIME
        - Boolean -> BIT
2. @Column, @GeneratedValue 가 포함된 클래스를 이용하여 create 쿼리 만들기
    - @Column 에 name 속성이 있을 경우, name 속성 값으로 저장된다.
    - @Column 에 length 속성의 default 값은 255이다.
    - @Column 에 nullable 속성의 default 값은 true 다.
    - @Column 에 name 속성이 없을 경우 필드 명에 스네이크케이스를 컬럼 명으로 한다.
3. @Table, @Transient 가 포함된 클래스를 이용하여 create 쿼리 만들기
    - @Table 에 name 속성이 있을 경우, name 속성 값으로 저장된다.
    - @Transient 이 붙은 컬럼은 database 에 저장하지 않는다.
4. @Entity, @Table 을 고려하여 drop 쿼리 만들기
   - @Table 에 name 속성이 있을 경우, name 속성 값으로 삭제한다.
   - @Table 에 name 속성이 없을 경우 클래스 명에 스네이크케이스를 테이블 명으로 한다.

## 🚀 3단계 - QueryBuilder DML

1. insert 구현
2. 모두 조회(findAll) 기능 구현
3. 단건 조회(findById) 기능 구현
4. delete 구현

## 🚀 4단계 - Simple Entity Object

1. find 구현
   - id 가 null 일 경우 예외가 발생한다.
   - id 로 조회되지 않는 경우 null 을 반환한다.
2. persist (insert) 구현
3. remove (delete) 구현
