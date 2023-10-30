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
- 무상태 애플리케이션은 컨테이너 수만 늘리면 스케일이 되고 롤링 배포로 서비스 중단없이 업데이트 배포 할 수 있다.
- 퍼시스턴시를 위해 디스크를 사용해야 되고 스토리지 관련 복잡한 상황이 생긴다. -> 도커 볼륨과 마운트, 컨테이너 파일 시스템

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