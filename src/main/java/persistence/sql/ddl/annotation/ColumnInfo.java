package persistence.sql.ddl.annotation;

import jakarta.persistence.Column;
import persistence.sql.ddl.ColumnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColumnInfo implements AnnotationInfo {

    private Column column;

    public ColumnInfo(Field field) {
        initialize(field);
    }

    @Override
    public void initialize(Field field) {
        // TODO Field에 어노테이션 없는 케이스 테스트
        this.column = field.getAnnotation(Column.class);
    }

    @Override
    public List<ColumnOption> metaInfos() {
        List<ColumnOption> result = new ArrayList<>();
        if (!column.nullable()) {
            result.add(ColumnOption.NOT_NULL);
        }

        return result;
    }

}
