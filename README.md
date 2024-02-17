# jpa-query-builder

### Step2 미션
1. [x] 요구사항 1 : Id annotation과 멤버변수를 Reflection API를 활용해 create query 생성하기 
2. [x] 요구사항 2 : `@Column`의 `name` 속성과 `nullable` 속성, `@GeneratedValue`가 반영되어 create query 가 생성되도록 개선한다. 
   - `@Table`의 `name`속성대로 column 명이 생성된다.
   - `@Table`의 `nullable`이 false면 not null 제약조건이 추가된다.
   - `@GeneratedValue`의 `strategy`에 따라 `AUTO_INCREMENT` 제약조건이 추가된다.
3. [x] 요구사항 2 : `@Table`의 `name` 속성과 `@Transient`가 반영되어 create query 가 생성되도록 개선한다. 
   - `@Table`의 `name`속성대로 table 명이 생성된다.
   - `@Transient` 는 column으로 매핑되지 않는다.