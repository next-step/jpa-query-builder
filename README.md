# jpa-query-builder

## 1단계 : Reflection

1. [x] 클래스의 모든 정보 조회
2. [x] 메소드 실행
3. [x] 특정 어노테이션이 작성된 메소드 실행
4. [x] 내부 필드 수정
5. [x] 인자를 가진 생성자를 통해 인스턴스 생성

### 리뷰 반영

1. [x] 클래스의 필드, 생성자, 메서드 테스트 분리 및 반환 순서가 고정되어 있지 않은 `Class.getXXX()` 메서드 테스트 수정
2. [x] `assertSoftly` 적용

## 2단계 : 테이블 생성 및 삭제

1. [x] 클래스의 필드 정보를 읽어서 컬럼 DDL을 문자열로 반환하는 `Column` 클래스 추가
2. [x] `@Entity`, `@Id`가 작성된 클래스 기준 create 쿼리 생성
3. [x] `@Table`, `@Column`, `@GeneratedValue`, `@Transient` 처리 추가
4. [x] Column에 몰려있던 파싱과 검증 책임을 parse 패키지에 위임
    - [x] ColumnParser와 TableParser 인터페이스 구현체를 가진 ParseConfig 추가
    - [x] ParseType 추가
    - [x] DefinitionQueryManager 생성자에 대한 복잡도를 감추기 위해 QueryManagerFactory 추가 

### 엣지 케이스

1. 스프링의 컴포넌트 스캔처럼 `@Entity`가 작성된 클래스들을 모두 읽어들이고 싶은데 어떤 방법이 있을까?
2. `@Id` 어노테이션이 여러 개라면 복합키를 생성
