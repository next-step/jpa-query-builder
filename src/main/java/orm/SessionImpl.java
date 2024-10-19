package orm;

import orm.dsl.QueryBuilder;
import orm.row_mapper.DefaultRowMapper;
import orm.settings.JpaSettings;

public class SessionImpl implements EntityManager {

    private final QueryBuilder queryBuilder;
    private final JpaSettings settings;

    public SessionImpl(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
        this.settings = JpaSettings.ofDefault();
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return queryBuilder.selectFrom(clazz).findById(id)
                .fetchOne(new DefaultRowMapper<>(clazz));
    }

    /**
     * 엔티티메니저에서는 bulk insert가 불가능 하지만 QueryBuilder 를 직접 쓰면
     * 충분히 가능함.
     *
     * step2 insert 먼저 구현
     * @param entity 엔티티 클래스
     * @return 엔티티
     */
    @Override
    public Object persist(Object entity) {
        return queryBuilder.insertInto(entity.getClass())
                .values(entity)
                .returnWithAIkey();
    }

    @Override
    public void remove(Object entity) {
        queryBuilder.deleteFrom(entity.getClass());
    }
}
