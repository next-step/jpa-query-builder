# jpa-query-builder

- [X] 엔티티는 엔티티 필드를 관리한다.
    - [X] @Id가 있는것은 엔티티 아이디로 관리한다.
        - [X] @Id가 붙은것은 무조건 1개여야 한다.
            - [X] 아니면 실패한다
    - [X] 필드 전체를 관리한다.
        - [X] @Transient가 붙은것은 무시한다.
    - [X] 테이블의 이름을 가진다.
        - [X] @Table가 있으면 해당 이름을 사용한다.
        - [X] 만약 없으면 클래스 명을 사용한다.
    - [X] @Entity가 없으면 실패한다.
- [X] 엔티티 아이디는 엔티티의 아이디를 관리한다
    - [X] @Id 가 붙은 애들을 PK로 지정한다.
    - [X] @GenerateValue의 타입에 따라 아이디 생성 전략을 설정한다.다
    - [X] @GeneratedValue가 있으면, 전략은 @GeneratedValue의 값을 따라간다.
        - [X] @GeneratedValue가 없으면, Auto 전략을 따라간다.
- [X] 엔티티 필드는 엔티티 클래스 내부의 필드들을 관리한다.
    - [X] @Column이 붙지 않은 필드들은 디폴트 값을 따라간다.
    - [X] DB의 컬럼명은 @Column의 name을 따라간다.
        - [X] 만약 없으면 필드의 이름을 따라간다.
    - [X] DB의 nullable은 @Column의 속성을 따라간다.
        - [X] 만약 없으면 true이다.
- [X] CreateDDL는 엔티티 클래스를 기반으로 쿼리를 만들어 낼 수 있다.
- [X] DropDDL은 엔티티 클래스를 기반으로 쿼리를 만들어 낼 수 있다.
- [ ] SelectDDL은 엔티티 클래스를 기반으로 쿼리를 만들어 낼 수 있다.
- [ ] DeleteDDL는 엔티티 클래스를 기반으로 쿼리를 만들어 낼 수 있다.
