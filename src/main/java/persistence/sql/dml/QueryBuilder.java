package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder implements Database{

    private static final String INSERT_QUERY_FORMAT = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String SELECT_QUERY_FORMAT = "SELECT * FROM %s";
    Class<?> clazz;
    public QueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public ResultSet executeQuery(String sql) {

        try {

        } catch (Exception e) {

        }
         return null;
    }

    public List<Object> findAll() {
        String sql = String.format(SELECT_QUERY_FORMAT, getTableName());
        ResultSet rs = executeQuery(sql);
        List<Object> results = new ArrayList<>();

        try {
            while(rs.next()) {
                Object instance = clazz.getDeclaredConstructor().newInstance();

                for(Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);

                    if(!field.isAnnotationPresent(Transient.class)) {
                        Object value = rs.getObject(field.getName());
                        field.set(instance, value);
                    }
                }
                results.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
    public String insert() {
        return String.format(INSERT_QUERY_FORMAT, getTableName(), columnsClause(clazz), valueClause(setValueData()));
    }
    public String columnsClause(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .map(this::getColumnName)
                .collect(Collectors.joining(", "));
    }

    public String valueClause(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(object);
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

    public Object setValueData() {
        Person person = new Person();
        person.setAllData(1L,"jskim", 33, "qazwsx3745@naver.com");
        person.setAllData(2L,"ian", 30, "aa@naver.com");
        return person;
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
