# jpa-query-builder



## 1단계 - Reflection
* **요구사항 1 - Car 클래스의 정보 출력**
  - [X] src/test/java/persistence/study > Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
* **요구사항 2 - Car 클래스의 test로 시작하는 메소드 실행**
  - [X] Car 객체의 메소드 중 test로 시작하는 메소드를 자동으로 실행한다. 
  - [X] 같이 Car 클래스에서 test로 시작하는 메소드만 Java Reflection을 활용해 실행하도록 구현한다.
  - [X] 구현은 src/test/java/persistence/study > ReflectionTest 클래스의 testMethodRun() 메소드에 한다.
