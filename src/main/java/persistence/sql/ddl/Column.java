package persistence.sql.ddl;

import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class Column {

    private static final Logger logger = LoggerFactory.getLogger(Column.class);
    private String name;
    private ColumnType columnType;
    private boolean primary;

    public Column(Field field) {
        try{
            this.name = field.getName();
            this.columnType = ColumnType.of(field.getType());
            this.primary = field.isAnnotationPresent(Id.class);
        } catch (Exception e) {
            logger.info("{} 으로 정의된 필드의 타입({})이 정의되지 않았습니다.", field.getName(), field.getType());
        }
    }

    public String getName() {
        return name;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public boolean isPrimary() {
        return primary;
    }
}
