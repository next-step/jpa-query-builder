# 만들면서 배우는 JPA

## 1. SQL 쿼리 빌더 구현

### 1-1. Reflection

- [x] 클래스 정보 출력
- [x] test로 시작하는 메소드 실행
- [x] @PrintView 애노테이션 메소드 실행
- [x] private field에 값 할당
- [x] 인자를 가진 생성자의 인스턴스 생성

### 1-2. QueryBuilder DDL

- [x] @Entity 애너테이션으로 테이블 스캔하는 스캐너 구현
- [x] DDL Query를 구성하는 열 정보, 제약조건 정보 컴포넌트화
- [x] DDL Query 컴포넌트 조합하는 Query Builder 구현
- [x] 테이블 스캐너에서 DDL Query 빌드하는 로직 구현
- [x] @Id, @GeneratedValue, @Column(name, nullable), @Transient 적용
- [x] CREATE, DROP 쿼리 빌더 구현

### 1-3. QueryBuilder DML

- [ ] Insert query의 columnsClause, valueClause 구현
- [ ] InsertQueryBuilder 구현
