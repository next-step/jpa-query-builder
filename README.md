# jpa-query-builder

### Step2 미션
1. [x] 요구사항 1 : Id annotation과 멤버변수를 Reflection API를 활용해 create query를 생성한다.
2. [x] 요구사항 2 : `@Column`의 `name` 속성과 `nullable` 속성, `@GeneratedValue`가 반영되어 create query 가 생성되도록 개선한다. 
   - `@Column`의 `name`속성대로 column 명이 생성된다.
   - `@Column`의 `nullable`이 false면 not null 제약조건이 추가된다.
   - `@GeneratedValue`의 `strategy`에 따라 `AUTO_INCREMENT` 제약조건이 추가된다.
3. [x] 요구사항 3 : `@Table`의 `name` 속성과 `@Transient`가 반영되어 create query 가 생성되도록 개선한다. 
   - `@Table`의 `name`속성대로 table 명이 생성된다.
   - `@Transient` 는 column으로 매핑되지 않는다.
4. [x] 요구사항 4 : drop query를 생성한다.

### Step3 미션

1. [x] 요구사항 1 : Entity Class의 Annotation정보를 바탕으로 insert query를 생성한다.
2. [x] 요구사항 2 : Entity Class를 넣어주면 해당 테이블의 모든 데이터가 반환되는 find all query를 생성한다.
3. [x] 요구사항 3 : Entity Class와 Id를 넣어주면 해당 테이블의 Id 에 해당하는 단건의 findById query를 생성한다.
4. [x] 요구사항 4 : Entity 를 넣어주면 해당 entity의 값들을 조건절에 추가해 delete query를 생성한다.
