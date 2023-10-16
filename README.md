# jpa-query-builder

## 2단계 - QueryBuilder DDL

### 요구사항 1
```java
@Entity
public class Person {
    
    @Id
    private Long id;
    
    private String name;
    
    private Integer age;
    
}
```
- @Entity 어노테이션이 달린 클래스를 가지고 create 쿼리를 만들 수 있다.
- 필드 타입과 명을 받아 추가할 수 있다.
- @Id 어노테이션이 붙는 경우 primary key로 지정할 수 있다.
