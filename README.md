# jpa-query-builder

### CreateQueryBuilder
- create 쿼리 생성

### DropQueryBuilder
- drop 쿼리 생성

### MappingData
- ColumnData
  - Entity 클래스의 필드에서 컬럼 정보를 추출
  - 추출한 정보 저장

- Columns
  - ColumnData 한번에 여러개 생성
  - 생성시 조건 검증

- TableData
  - Entity 클래스의 테이블 정보를 추출
  - 추출한 정보저장

### Dialect
- 여러 DB 확장을 위해 추출정보를 각 DB에 맞는 용어로 변환하는 인터페이스

## 요구사항
### step2
1. 아래 정보를 바탕으로 create 쿼리 만들어보기
    - 클래스 이름으로 에서 테이블 이름 추출
    - 필드 이름과 타입으로 컬럼 이름과 타입 추출
    - @Id 있는 필드는 PK로 추출
2. 추가된 정보를 통해 create 쿼리 만들어보기
    - @GeneratedValue 있는 필드는 알 맞은 생성 전략 추출
    - @Column 의 name 속성 값으로 컬럼명 추출
    - @Column 의 nullable 속성 값으로 컬럼의 null 여부 추출
3. 추가된 정보를 통해 create 쿼리 만들어보기2
    - @Table 의 name 속성 값으로 테이블명 추출
    - @Transient 있는 필드는 무시하기
4. 정보를 바탕으로 drop 쿼리 만들어보기
    - 테이블명으로 drop 쿼리 만들기

### step3
1. insert 구현해보기
   - columns 과 values 를 나누어서 구현
   - @Transient 무시하기
   - @Column 에서 목표 테이블 이름 얻기
   - @GeneratedValue 는 비워놓고 쿼리 생성
2. findAll 구현해보기
   - `select * from 테이블명` 쿼리 생성
3. findById 구현해보기
    - `select * from 테이블명 where id = ?` 쿼리 생성
    - BooleanBuilder로 동적으로 만들기
4. delete 구현해보기
    - `delete from 테이블명 where id = ?` 쿼리 생성
    - BooleanBuilder로 동적으로 만들기
