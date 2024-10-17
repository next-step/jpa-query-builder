# jpa-query-builder

## 1단계 - Java Reflection

- [x] src/test/java/persistence/study > Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
- [x] test로 시작하는 메소드 실행
- [x] @PrintView 애노테이션 메소드 실행
- [x] private field에 값 할당
- [x] 인자를 가진 생성자의 인스턴스 생성

## 2단계 - QueryBuilder DDL

- [x] Entity Class의 정보를 바탕으로 create 쿼리 만들어보기
  - [x] 엔티티에 대한 Validation을 진행한다.
    - [x] @Entity 애노테이션을 가지고 있는지 확인한다.
    - [x] @Id 애노테이션을 가지고 있는지 확인한다.
      - [x] @Id 애노테이션을 가지고 있는 필드가 하나인지 확인한다.
  - [x] Id 생성 전략에 따라 쿼리를 생성한다.
    - [x] GenerationType.IDENTITY 전략일 경우, auto increment를 사용한다.
    - [x] GenerationType이 선언되지 않았을 경우 AUTO를 사용한다.
  - [x] Table 명을 설정한다.
    - [x] @Table 애노테이션이 선언되어 있을 경우, 해당 값을 사용한다.
    - [x] @Table 애노테이션이 선언되어 있지 않을 경우, @Entity의 name 값을 사용한다.
      - [x] @Entity 의 name 값이 없을 경우, 클래스명을 사용한다.
  - [x] Column 명을 설정한다.
    - [x] @Column 애노테이션이 선언되어 있을 경우, 해당 값을 사용한다.
    - [x] @Column 애노테이션이 선언되어 있지 않을 경우, 필드명을 사용한다.
      - [x] @Column의 속성은 선언되어 있으면 해당 값을 사용한다.
      - [x] @Column의 속성이 선언되어 있지 않을 경우, @Column의 기본 값을 사용한다.
  - [x] nuallable 여부를 설정한다.
    - [x] @Column의 nullable 속성을 사용한다.
    - [x] @Column의 nullable 속성이 선언되어 있지 않을 경우, true를 사용한다.
  - [x] Column의 길이를 설정한다.
    - [x] @Column의 length 속성을 사용한다.
    - [x] @Column의 length 속성이 선언되어 있지 않을 경우, 255를 사용한다.
- [x] Entity Class의 정보를 바탕으로 drop 쿼리 만들어보기

## 3단계 - QueryBuilder DML

- [x] Person 정보를 바탕으로 insert 구현해보기
  - [x] column clause와 value clause를 나누어 작성한다. 
- [x] Person 정보를 바탕으로 select * (findAll) 구현해보기
- [x] Person 정보를 바탕으로 select * where (findById) 구현해보기
- [x] Person 정보를 바탕으로 delete 구현해보기

## 4단계 - Simple Entity Object 
- [ ] 간이 Entity Manager Interface 구현해보기
  - [x] 요구사항1 - find
  - [x] 요구사항2 - persist (insert)
  - [x] 요구사항3 - delete
  - [x] 요구사항4 - update
    - [x] update query builder 생성.
      - [x] null 값인 필드도 포함해서 query 생성
