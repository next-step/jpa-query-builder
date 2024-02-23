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

## DML QueryBuilder

* INSERT
    * @Id 필드를 제외한 필드들에 대해 Map 으로 구성된 row 를 INSERT 하는 구문을 생성합니다.
* SELECT
    * 전체 row 를 조회하는 SELECT 구문을 생성합니다.
    * id 로 한 row 를 조회하는 SELECT 구문을 생성합니다.
* DELETE
    * 조건에 맞춰 삭제하는 DELETE 구문을 생성합니다.

## 커밋 전에 확인할 것

* import 정리
* else 키워드를 줄여보기
* 테스트 코드는 충분한가?
* TODO 를 잘 마무리했는가?
* 생성자에 접근자를 잘 적용했나?
* 일관되게 작성했나? (비슷한 일을 하는 클래스가 여러 스타일로 작성됐는가?)
* 불필요하게 쪼개져있진 않은가?

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
- [v] 요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기
- [v] 요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기
- [v] 요구사항 4 - 위의 정보를 바탕으로 delete 쿼리 만들어보기

## 4단계 - Simple Entity Object

- [v] 요구사항1 - find
- [v] 요구사항2 - persist (insert)
- [v] 요구사항3 - remove (delete)
