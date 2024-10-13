# jpa-query-builder
## 🚀 1단계 - Reflection
### 요구사항
- [ ] 클래스 정보 출력: Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력
- [ ] "test" 로 시작하는 메소드 실행
- [ ] @PrintView 애노테이션 메소드 실행
- [ ] private field 에 값 할당
- [ ] 인자를 가진 생성자의 인스턴스 생성

## 🚀 2단계 - QueryBuilder DDL
### 요구사항
- [ ] Person 클래스에 대한 create 쿼리 생성
  - [ ] @Entity, @Id 처리
  - [ ] @Entity, @Id, @GeneratedValue, @Column 처리
  - [ ] @Table, @Entity, @Id, @GeneratedValue, @Column, @Transient 처리
- [ ] Person 클래스에 대한 drop 쿼리 생성
  - [ ] @Entity, @Table 처리
