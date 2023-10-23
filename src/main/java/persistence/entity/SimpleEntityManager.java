package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import persistence.common.FieldClazz;
import persistence.common.FieldClazzList;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class SimpleEntityManager implements EntityManager {

    private final JdbcTemplate template;

    public SimpleEntityManager(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        String query = new SelectQueryBuilder().findById(clazz, Arrays.asList(Id));
        FieldClazzList fieldClazzList = new FieldClazzList(clazz);
        return template.queryForObject(query, resultSet -> {
            try {
                if(!resultSet.next()){
                    return null;
                }
                T entity = clazz.newInstance();
                for (FieldClazz fc : fieldClazzList.getFieldClazzList()) {
                    Object value = resultSet.getObject(fc.getName(), fc.getClazz());
                    fc.set(entity, value);
                }
                return entity;
            } catch (InstantiationException | IllegalAccessException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Object persist(Object entity) {
        String query = new InsertQueryBuilder().getQuery(entity);
        template.execute(query);
        return entity;
    }

    @Override
    public void remove(Object entity) {
        String query = new DeleteQueryBuilder().delete(entity);
        template.execute(query);
    }
}
