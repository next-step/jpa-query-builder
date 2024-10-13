package orm.settings;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import orm.util.StringUtils;

import java.lang.reflect.Field;

/**
 * 엔티티 필드에 @Table @Column 어노테이션을 사용하지 않았을 때,
 * DB 리소스명을 스네이크로 강제한다. (eg. 테이블명, 컬럼명)
 */
public class SnakeForPropertyNamingStrategy implements NamingStrategy {

    @Override
    public String namingColumn(Column column, Field field) {
        if (column != null && StringUtils.isNotBlank(column.name())) {
            return column.name();
        }

        final String fieldName = field.getName();
        return fieldName.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    @Override
    public <ENTITY> String namingTable(Class<ENTITY> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && StringUtils.isNotBlank(tableAnnotation.name())) {
            return tableAnnotation.name();
        }

        final String tableName = entityClass.getName();
        return tableName.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
