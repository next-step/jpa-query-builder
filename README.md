# jpa-query-builder

## step1

요구사항
- [x] 클래스 정보 출력
  - Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
- [ ] test로 시작하는 메소드 실행
    - Car 객체의 메소드 중 test로 시작하는 메소드를 자동으로 실행한다.
- [ ] @PrintView 애노테이션 메소드 실행
    - @PrintView애노테이션이 설정되어 있는 메소드를 자동으로 실행한다.
- [ ] private field에 값 할당
    - Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
- [ ] 인자를 가진 생성자의 인스턴스 생성
    - Car 클래스의 인스턴스를 자바 Reflection API를 활용해 Car 인스턴스를 생성한다.
