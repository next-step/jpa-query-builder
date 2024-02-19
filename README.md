# jpa-query-builder 요구사항

## 🚀 1단계 - Reflection

1. Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
2. Car 객체의 메소드 중 test로 시작하는 메소드를 자동으로 실행한다.
3. @PrintView annotation 설정되어 있는 메소드를 자동으로 실행한다.
4. java Reflection API를 활용해 다음 Car 클래스의 name과 price 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.
5. Car 클래스의 인스턴스를 java Reflection API를 활용해 Car 인스턴스를 생성한다.

## 🚀 2단계 - QueryBuilder DDL

1. @Entity, @Id 가 포함된 클래스를 이용하여 create 쿼리 만들기
   - @Id 가 붙은 컬럼이 반드시 존재해야한다.
2. @Column, @GeneratedValue 가 포함된 클래스를 이용하여 create 쿼리 만들기
3. @Table, @Transient 가 포함된 클래스를 이용하여 create 쿼리 만들기
4. @Entity, @Table 을 고려하여 drop 쿼리 만들기
