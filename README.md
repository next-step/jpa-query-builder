# jpa-query-builder



# 3단계 - QueryBuilder DML

## 1.요구사항 Insert 구현하기
- MetaEntity 로부터 Columns를 뽑아서 Columns를 추출한다. 
- MetaEntity 로부터 Fields의 값들을 뽑아서 Values를 추출한다.
- % Primary key를 제외해서 entity에서 추출되어야한다.
```sql
insert into table (id, nick_name, old, email) [COLUMNS] values (1, 'value2', 23, 'ASDASD@EMAIL.COM') [VALUES]
```

## 2. 요구사항 SELECT 구현하기
- MetaEntity 로부터 Columns를 뽑아서 COLUMNS를 추출한다.
- MetaEntity 로부터 Table을 뽑아서 TABLE 이름을 추출한다.
- % Primary key를 포함하여서 Insert와 다르게 entity에서 추출되어야한다.
```sql
SELECT id, nick_name, old, email[COLUMNS] from users [TABLE]
```

## 3. 요구사항 WHERE 구현하기
- MetaEntity 로부터 Columns를 뽑아서 COLUMNS를 추출한다.
- MetaEntity 로부터 Table을 뽑아서 TABLE 이름을 추출한다.
- % Primary key를 포함하여서 Insert와 다르게 entity에서 추출되어야한다.

```sql
SELECT id, nick_name, old, email [COLUMNS] from users [TABLE] where id = ? [WHERE]
```

## 4. 요구사항 DELETE 구현하기

```sql
DELETE FROM users [TABLE] where id = ? [WHERE]
```

## 공통 구현 사항
- Column 절 (모든 컬럼으로 제한) (nullable 도 고려하면 진짜 어렵다.)
- Where 절 (ID로 제한)
- INSERT, SELECT, DELETE
- 실제로 QUERY를 돌려서 테스트 필요하다.
- Fixture가 필요함

## 고민사항
- Insert 시의 id 컬럼을 필터해서 가져오는 것
- 테스트 어떻게 할지?(통합? 유닛?)
- 어떤 방식으로 insert는 사용 되는지 컨텍스트를 이해하기 어려웠다.


# 4단계 - Simple Entity Object

## 1.요구사항 EntityManager의 find 구현하기
- QueryBuilder를 사용해서 id를 가진 entity를 select 합니다.
  - Class 나 Instance로 부터 MetaData 추출
  - 쿼리 생성
  - RowMapper로 가져오고 Instance들 생성 (객체 맵핑)
## 2. 요구사항 EntityManager의 persist 구현하기
- QueryBuilder를 사용해서 entity를 save 합니다.
  - Instance로 부터 MetaData 추출
  - 쿼리 생성
  
## 3. 요구사항 EntityManager의 remove 구현하기
- QueryBuilder를 사용해서 entity를 delete 합니다.
  - Instance로 부터 MetaData 추출
  - 쿼리 생성

## 공통 구현 사항
- MetaEntity의 책임: Fields 값, pk 값, instance의 값 변경
- EntityManager의 책임: 
  - Entity가 주어질때 
    - 1. 메타데이터 생성
    - 2. (쿼리, 저장, 삭제)을 쿼리빌더를 통해 쿼리 생성
    - 3. 가져온 Entity값을 row mapper를 이용해 인스턴스들 생성
  - QueryBuilder의 Metadata 추출 작업을 EntityManager의 책임으로 이관
- RowMapper 의 책임:
  - 1. Instance 생성
  - 2. 쿼리에 대한 값 받아오기


## 고민사항
- 구현 인터페이스에 따른 queryBuilder 의 호환이 맞지 않아 리팩토링이 필요했다.
- metaData의 생성에 대한 책임을 누가 가지고 갈지?
- EntityManager의 책임과 QueryBuilder의 책임을 정함