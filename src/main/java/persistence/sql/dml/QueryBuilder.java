package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder{

    private static final String INSERT_QUERY_FORMAT = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String SELECT_QUERY_FORMAT = "SELECT * FROM %s";
    private JdbcTemplate jdbcTemplate;
    Class<?> clazz;
    public QueryBuilder(Class<?> clazz, JdbcTemplate jdbcTemplate) {
        this.clazz = clazz;
        this.jdbcTemplate = jdbcTemplate;
    }

    public QueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<Person> findAll(JdbcTemplate jdbcTemplate) {
        String sql = String.format(SELECT_QUERY_FORMAT, getTableName());
        PersonRowMapper personRowMapper = new PersonRowMapper();

        return jdbcTemplate.query(sql, personRowMapper);
    }


    public void run(JdbcTemplate jdbcTemplate) {
        List<Person> personData = generatePersonData();

        for(Person person : personData) {
            String insertQuery = insert(person);
            jdbcTemplate.execute(insertQuery);
        }

    }
    public String insert(Object entity) {
        return String.format(INSERT_QUERY_FORMAT, getTableName(), columnsClause(clazz), valueClause(entity));
    }

    public Person findById(JdbcTemplate jdbcTemplate, Long id) {
        String selectQuery = "SELECT * FROM users";
        String query = whereClause(selectQuery, id);
        return jdbcTemplate.queryForObject(query, new PersonRowMapper());
    }

    public void deleteById(JdbcTemplate jdbcTemplate, Long id) {
        String deleteQuery = "DELETE FROM users";
        String query = whereClause(deleteQuery, id);
        jdbcTemplate.executeUpdate(query);
    }

    private String whereClause(String query, Long id) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(query);
        stringBuilder.append(" where id = " + id);

        return stringBuilder.toString();
    }
    public String columnsClause(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .map(this::getColumnName)
                .collect(Collectors.joining(", "));
    }

    public String valueClause(Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        if(value instanceof  String) {
                            return "'" + value + "'";
                        }
                        return String.valueOf(value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining(", "));
    }
    public List<Person> generatePersonData() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1L,"jskim", 33, "qazwsx3745@naver.com"));
        personList.add(new Person(2L,"ian", 30, "aa@naver.com"));

        return personList;
    }

    public String getColumnName(Field field) {
        if(field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isEmpty()) {
            return field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }

    public String getTableName() {
        if(clazz.isAnnotationPresent(Table.class)){
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getName();
    }
}
