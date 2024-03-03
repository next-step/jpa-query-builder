# jpa-query-builder

## Step4 
- [x] 3단계 피드백 반영
  - [x] clause를 접미사로 갖도록 클래스명 변경
  - [x] Generic 제거
  - [x] 테스트 코드 추가, 테스트가 용이하도록 코드 변경
## Step3
- [x] 요구사항 1 - insert 쿼리 구현하기
- [x] 요구사항 2 - findAll 기능 구현하기
- [x] 요구사항 3 - findById 구현하기
- [x] 요구사항 4 - delete 쿼리 구현히라
  - [x] 4.1 deleteAll 구현하라
  - [x] 4.2 deleteById 구현하라
- [x] 2단계 피드백 반영
  - [x] 단순히 값을 리턴할 때는 get을 붙이지 말아라
  - [x] 객체 생성시 유효한 값을 상태값으로 가지도록 하여, 싱태에 대해 null check를 따로 하지 않도록 바꾼다
  - [x] 변수명 변경 : Map -> Converter
  - [x] 클래스명 변경: Id -> PrimaryKey


## Step2
- [x] step1 리뷰 사항 반영
- [x] 요구사항 1 - @Column 애노테이션이 없는 Person 엔티티를 이용하여 create 쿼리 만들기
- [x] 요구사항 2 - @Column 애노테이션이 있는 Person 엔티티를 이용하여 create 쿼리 만들기
- [x] 요구사항 3 
  - [x] 3.1 @Table 애노테이션이 붙은 필드의 name을 테이블명으로 가지는 create 쿼리 만들기
  - [x] 3.2 @Transient 애노테이션이 붙은 필드는 제하고 create 쿼리 만들기
- [x] 요구사항 4 - Person drop 쿼리 만들기

### step2 질문사항
- 테스트코드 작성시 throws exception을 해도 될까? try catch로 에러를 일일히 처리하자니 테스트 코드가 지저분하다.
  - 답변: 가독성이 떨어지므로 try-catch 대신 exception 던지자.

## Step1
- [x] Car 클래스 추가
- [x] 요구사항 1 - 클래스 정보 출력
- [x] 요구사항 2 - test로 시작하는 메소드 실행
- [x] 요구사항 3 - @PrintView 애노테이션 메소드 실행
- [x] 요구사항 4 - private field에 값 할당
- [x] 요구사항 5 - 인자를 가진 생성자의 인스턴스 생성
