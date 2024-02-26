package jdbc;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

// TODO: (질문) 패키지 위치를 jdbc 하위로 잡아도 되나요? JdbcTemplate과 인접하게 있어야할지, 아니면 제가 직접 구현한 기능이니 persistence 하위에 있어야할지 질문드립니다.
public class DtoMapper<T> implements RowMapper{

    private final Class<T> clazz;

    public DtoMapper(Class<T> clazz) {
        this.clazz = clazz;
    }
    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        T dto;
        try { // TODO:(질문) two depth 를 지양하라고 하셨는데, try-catch 문도 이에 해당할까요?
            dto = clazz.getDeclaredConstructor().newInstance();
            List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).filter(x -> !x.isAnnotationPresent(Transient.class)).toList();
            for (Field field : fields) {
                field.setAccessible(true);
                String columnName = field.getAnnotation(Column.class) == null || field.getAnnotation(Column.class).name().isEmpty()? field.getName() : field.getAnnotation(Column.class).name();
                Object value = resultSet.getObject(columnName);
                field.set(dto, value);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("DtoMapper가 정상 동작하지 않습니다.", e);
        }
        return dto;
    }

}
