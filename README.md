# jpa-query-builder

## 2단계 - QueryBuilder DDL

### 요구 사항

- [ ] create query를 만든다.
  - [X] Long은 bigint로 해석한다.
  - [X] String은 varchar(255)로 해석한다.
  - [X] Integer는 integer로 해석한다.
  - [ ] 클래스에 `@Entity`가 없으면 예외가 발생한다.
  - [X] 필드에 `@Id`가 없으면 예외가 발생한다.
  - [X] `@Id`가 사용된 컬럼은 not null 제약조건을 갖는다.
  - [X] `@Id`가 사용된 컬럼은 primary key가 된다.
- [ ] `@GeneratedValue`와 `@Column` 정보를 추가로 읽어 create query를 만든다.
- [ ] `@Table`과 `@Transient` 정보를 추가로 읽어 create query를 만든다.
- [ ] drop query를 만든다.

## 1단계 - Reflection

### 요구 사항

- [X] Car 클래스의 모든 필드 정보를 출력한다.
- [X] Car 클래스의 모든 생성자 정보를 출력한다.
- [X] Car 클래스의 모든 메서드 정보를 출력한다.
- [X] test로 시작하는 메서드를 실행한다.
- [X] @PrintView 애노테이션 메서드를 실행한다.
- [X] private field에 값을 할당한다.
- [X] 인자를 가진 생성자를 사용하여 인스턴스를 생성한다.
