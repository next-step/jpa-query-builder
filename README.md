# jpa-query-builder

## 2단계 - QueryBuilder DDL

## 🚀 참고: 쿼리 실행 방법
```java
void executeQuery(String query) {
    final DatabaseServer server = new H2();
    server.start();

    final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

    server.stop();
}
```

## 요구사항
### 1. 아래 정보를 바탕으로 create 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다
```java
@Entity
public class Person {
    
    @Id
    private Long id;
    
    private String name;
    
    private Integer age;
    
}
```

### 2. 추가된 정보를 통해 create 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다   
> 아래의 정보를 통해 Person 클래스의 정보를 업데이트 해준다
```java
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;
    
    @Column(nullable = false)
    private String email;

}

```

### 3. 추가된 정보를 통해 create 쿼리 만들어보기2
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다   
> 아래의 정보를 통해 Person 클래스의 정보를 업데이트 해준다
```java
@Table(name = "users")
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(nullable = false)
    private String email;

    @Transient
    private Integer index;

}

```

### 4. 정보를 바탕으로 drop 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/ddl > 하위에 구현한다
> @Entity, @Table를 고려해서 잘 작성해보자


## QueryBuilder DDL 순서
1. AnnotatedTableScanner는 경로기반으로 Entity 애노테이션이 붙은 클래스 목록을 스캐닝해 반환합니다. 
2. QueryBuilder의 구현체인 H2QueryBuilder는 AnnotatedTableScanner를 통해 반환된 클래스 목록을 받아 DDL을 생성합니다.
   1.  buildTableQuery 함수를 통해 테이블명을 기반으로 CREATE TABLE 쿼리문자열을 생성합니다. 
   2. QueryColumnSupplier 구현체 목록들을 순회하며 각 필드별로 적절한 쿼리 빌드가 진행됩니다. 
   3. QueryConstraintSupplier 구현체 목록을 순회하며 각 필드별로 적절한 제약 조건을 빌드합니다.
3. H2QueryBuilder는 생성된 쿼리문자열을 반환합니다.
4. JdbcTemplate을 통해 쿼리를 실행합니다.

## 📦 DDLQueryBuilder package 구조
```
.
├── Application.java
└── sql
    └── ddl
        ├── Dialect.java ---------------------------- 데이터베이스 방언 인터페이스
        ├── QueryBuilder.java ----------------------- 쿼리 빌더 인터페이스
        ├── QueryColumnSupplier.java ---------------- 컬럼 쿼리 공급자 인터페이스
        ├── QueryConstraintSupplier.java ------------ 제약 조건 쿼리 공급자 인터페이스
        ├── QuerySupplier.java ---------------------- 쿼리 공급자 인터페이스
        ├── TableScanner.java ----------------------- 테이블 스캐너 인터페이스
        ├── config
        │   └── PersistenceConfig.java -------------- Persistence 설정
        ├── impl
        │   ├── AnnotatedTableScanner.java ---------- Entity 애노테이션 스캐너
        │   ├── ColumnGeneratedValueSupplier.java --- @GeneratedValue 쿼리 공급자
        │   ├── ColumnNameSupplier.java ------------- 컬럼명 쿼리 공급자
        │   ├── ColumnOptionSupplier.java ----------- 컬럼 옵션 쿼리 공급자
        │   ├── ConstraintPrimaryKeySupplier.java --- 기본키 제약 조건 쿼리 공급자
        │   ├── H2ColumnTypeSupplier.java ----------- H2 데이터베이스 컬럼 타입 쿼리 공급자
        │   ├── H2Dialect.java ---------------------- H2 데이터베이스 방언
        │   └── H2QueryBuilder.java ----------------- H2 쿼리 빌더 구현체
        ├── node
        │   ├── EntityNode.java --------------------- 엔티티 노드 인터페이스
        │   ├── FieldNode.java ---------------------- 필드 노드 인터페이스
        │   └── SQLNode.java ------------------------ SQL 노드 인터페이스
        └── util
            ├── CamelToSnakeConverter.java ---------- 카멜케이스 기반 이름 스네이크케이스 변환기
            └── NameConverter.java ------------------ 이름 변환 인터페이스

```


## 3단계 - QueryBuilder DML

## 요구사항
### 0. 사전 정보
```java
@Table(name = "users")
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(nullable = false)
    private String email;

    @Transient
    private Integer index;

}

```

### 1. 위의 정보를 바탕으로 insert 구현해보기
> 구현은 src/main/java/persistence > sql/dml > 하위에 구현한다   
> 위의 정보를 통해 Person 클래스의 정보를 업데이트 해준다   
> @Entity, @Table, @Id, @Column, @Transient 를 고려해서 잘 작성해보자

- columns 과 values 를 나누어서 구현해보자
  - insert into table (column1, column2, column3) values (value1, value2, value3)
```java
     private String columnsClause(Class<?> clazz) {
         //logic...
     }
 
     private String valueClause(Object object) {
        //logic... 
     }

```
### 2. 요구사항 2 - 위의 정보를 바탕으로 모두 조회(findAll) 기능 구현해보기
> 구현은 src/main/java/persistence > sql/dml > 하위에 구현한다   
> 쿼리 실행을 통해 데이터를 여러 row 를 넣어 정상적으로 나오는지 확인해보자

```java
public interface Database {
    // logic... 
    ResultSet executeQuery(String sql);
}

```

### 3. 요구사항 3 - 위의 정보를 바탕으로 단건 조회(findById) 기능 구현해보기
> 구현은 src/main/java/persistence > sql/dml > 하위에 구현한다

```java
private String whereClause(String selectQuery, Class<?> clazz) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(selectQuery);
    stringBuilder.append(" where ");
    //logic ...
}

```
### 4. 요구사항 4 - 위의 정보를 바탕으로 delete 쿼리 만들어보기
> 구현은 src/main/java/persistence > sql/dml > 하위에 구현한다   
> @Entity, @Table, @Id, @Column, @Transient 를 고려해서 잘 작성해보자





















