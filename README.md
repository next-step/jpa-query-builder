# jpa-query-builder

## DDL QueryBuilder

* CREATE TABLE
    * @Entity 어노테이션이 달려 있는 클래스의 CREATE TABLE 구문을 생성할 수 있습니다.
    * @Table 어노테이션의 name 을 인식해서 테이블명으로 사용합니다.
    * @Id 가 있을 경우 primary key 로 판단합니다.
    * @GeneratedValue strategy 가 IDENTITY 인 경우 auto_increment 를 추가합니다.
    * @Column 의 name, nullable 을 인식하고 구문에 추가합니다.
    * @Transient 필드는 구문에서 제외됩니다.
* DROP TABLE
    * @Entity 어노테이션이 달려 있는 클래스의 DROP TABLE 구문을 생성할 수 있습니다.
    * @Table 어노테이션의 name 을 인식해서 테이블명으로 사용합니다.

## 할 일들

### 1단계 - Reflection

- [v] 완료

### 2단계 - QueryBuilder DDL

- [v] @Entity 와 @Id 를 인식하여 CREATE 쿼리 생성
- [v] @GeneratedValue(strategy = GenerationType.IDENTITY), @Column(name, nullable) 인식하여 CREATE 쿼리 생성
- [v] @Transient 인식하여 CREATE 쿼리에서 제외
- [v] 유사한 방식으로 buildDeleteQuery() 구현

## 3단계 - QueryBuilder DML

- [v] 요구사항 1 - 위의 정보를 바탕으로 insert 구현해보기
- [ ] 요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기
- [ ] 요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기
- [ ] 요구사항 4 - 위의 정보를 바탕으로 delete 쿼리 만들어보기
- [ ] 2단계 리뷰 끝나면 코드 정리
    - QueryBuilderTest -> QueryBuilderDdlTest
    - OldPerson1,OldPerson2 -> Person1, Person2
    - Person.java 삭제
- [ ] ddl/dml 빌더를 인터페이스로 뽑아보기
-

## 4단계 - Simple Entity Object

- [ ] 요구사항1 - find
- [ ] 요구사항2 - persist (insert)
- [ ] 요구사항3 - remove (delete)
