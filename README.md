# jpa-query-builder

## 1단계 - Reflection

- [x] Car 클래스의 정보 출력
  - [x] 클래스의 모든 필드
  - [x] 생성자
  - [x] 메소드
- [x] test로 시작하는 메소드 실행
- [x] @PrintView 애노테이션 메소드 실행
- [x] private field에 값 할당
- [x] 인자를 가진 생성자의 인스턴스 생성

## 2단계 - QueryBuilder DDL

- [ ] Person Entity로 create 쿼리 만들어보기
  - [ ] 엔티티 이름을 테이블명으로 사용
  - [ ] 엔티티 필드를 컬럼으로 사용
  - [ ] @Id 어노테이션을 PRIMARY KEY로 지정
- [ ] NewPerson Entity로 create 쿼리 만들어보기
  - [ ] @Column.name 어노테이션을 컬럼명으로 사용
  - [ ] @Column.nullable 어노테이션을 NOT NULL로 사용
  - [ ] @GeneratedValue 어노테이션을 기본키 매핑으로 사용
- [ ] BrandNewPerson Entity로 create 쿼리 만들어보기
  - [ ] @Transient 어노테이션은 컬럼에서 제외
  - [ ] @Table 어노테이션을 테이블명으로 사용
- [ ] BrandNewPerson Entity로 drop 쿼리 만들어보기