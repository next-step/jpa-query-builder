# jpa-query-builder

## Step3
- [x] 요구사항 1 - insert 쿼리 구현하기
- [x] 요구사항 2 - findAll 기능 구현하기
- [x] 요구사항 3 - findById 구현하기
- [ ] 요구사항 4 - delete 쿼리 구현하


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
