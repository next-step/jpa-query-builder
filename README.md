# jpa-query-builder

## 2단계 - QueryBuilder DDL

### 요구 사항

- [ ] `@Entity`, `@Id`가 사용된 클래스를 받아 create query를 만든다.
  - [ ] Long은 bigint로 해석한다.
  - [ ] String은 varchar(255)로 해석한다.
  - [ ] Integer는 integer로 해석한다.
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
