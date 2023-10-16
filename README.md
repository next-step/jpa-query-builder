# jpa-query-builder

## 요구사항
### 1. create query 만들기
1. @Entity가 붙은 클래스를 대상으로 만든다.
2. java type과 H2 dataType을 매핑해야한다.
3. 테이블명은 class명으로 만든다.
4. 컬럼명은 필드명으로 만든다.
5. @id가 붙은 컬럼은 primary key로 만든다.