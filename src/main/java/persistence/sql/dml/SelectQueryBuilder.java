package persistence.sql.dml;

import jakarta.persistence.Entity;
import jdbc.RowMapper;
import persistence.sql.ddl.Table;
import persistence.sql.ddl.column.Columns;
import persistence.sql.ddl.column.Values;
import persistence.sql.exception.InvalidEntityException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static persistence.sql.common.SqlConstant.CLOSING_PARENTHESIS;
import static persistence.sql.common.SqlConstant.COMMA;

public class SelectQueryBuilder {
    public static final String SELECT_ALL_QUERY = "SELECT * FROM %s";
    public static final String SELECT_BY_ID_QUERY = "SELECT * FROM %s where %s = %d";
    private final Table table; // TODO: (질문) table을 다 가지고 있는게 나을까? 아니면 필요한 정보만?
    private final Class<?> clazz;

    public SelectQueryBuilder(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
        this.table = new Table(entity);
        this.clazz = entity.getClass();
    }

    public String getFindAllQuery() {
        return String.format(SELECT_ALL_QUERY, table.getName());
    }
    public String getFindById(Long id) {
        return String.format(SELECT_BY_ID_QUERY, table.getName(), table.getIdName(), id);
    }
}
