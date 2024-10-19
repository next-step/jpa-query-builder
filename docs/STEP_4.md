## 요구사항

- 요구사항 1 - find
- 요구사항 2 - persist (insert)
- 요구사항 3 - remove (delete)
- 요구사항 4 - update

## 고민포인트
1. insert 문에서 단건 insert 와 bulk insert시 채번방식 문제.
- 단건 insert 시, PK를 채번해 넣어 줄 수 있지만, bulk insert 시, PK를 채번해 넣어 줄 수 없다. 
이건 DB 레벨에서 불가능한 문제이니 적절히 분기처리를 해야한다.

해결: Step을 BulkInsert와 Insert로 나누어서 처리하였다.  
BulkInsert는 PK를 채번하지 않고, Insert는 PK를 채번하여 처리하였다.
Step으로 분기처리하면 이런것도 가능하니 편하다. 이후 추가될 join도 이 아키텍처로 충분히 처리 가능해보임

2. RowMapper이거 어떻게 넣음?
여기에 RowMapper를 넣을만한 자리가 없다...
이후과제인것같긴한데 리플랙션으로 rowMapping 해주는거 구현해야할듯..
 
```Java
public interface EntityManager {

    <T> T find(Class<T> clazz, Long id);

    Object persist(Object entity);

    void remove(Object entity);
}
```


