# 4단계 - Simple Entity Object

```java
public interface EntityManager {

    <T> T find(Class<T> clazz, Long id);

    Object persist(Object entity); //or void persist(Object entity);

    void remove(Object entity);
}
```

- [X] 요구사항 1 - EntityManager 구현하기
- [ ] 테이블명과 컬럼명들을 class 로부터 정보를 얻어오는것과 실제 인스턴스로 부터 값을 얻어오는 부분 고민
- [ ] ColumnName, ColumnValue 일급컬렉션으로 재설계
