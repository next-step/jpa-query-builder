# jpa-query-builder

## 요구 사항
### Step1
- [x] 모든 필드를 출력한다.
- [x] 모든 생성자를 출력한다.
- [x] 모든 메소드를 출력한다.
- [x] 메소드 중 test로 시작하는 메소드를 실행한다.
- [x] @PrintView 어노테이션이 붙은 메소드를 실행한다.
- [x] name, price 라닌 필드에 값을 할당하고 getter 메소드로 값을 확인한다.
- [x] 인자를 가진 생성자의 인스턴스를 생성한다.

### Step2
- [x] Person 클래스를 생성한다.
- [x] Person 클래스에 대한 테이블을 생성하는 SQL CREATE 쿼리를 만든다.
- [ ] Reflection API를 사용하여 생성된 쿼리를 실행한다.
- [ ] @GeneratedValue와 @Column 어노테이션이 적용된 필드들이 반영된 SQL CREATE 쿼리를 생성한다.
- [ ] @Transient 어노테이션이 적용된 필드들을 제외한 SQL CREATE 쿼리를 생성한다.
- [ ] Person 클래스에 대한 테이블을 삭제하는 SQL DROP 쿼리를 만든다.

