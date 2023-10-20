package persistence.sql.ddl.annotation;

import jakarta.persistence.Column;
import persistence.sql.ddl.ColumnMetaInfo;

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
    public List<ColumnMetaInfo> getColumnMetaInfos() {
        List<ColumnMetaInfo> columnMetaInfos = new ArrayList<>();

        if (!column.nullable()) {
            columnMetaInfos.add(new ColumnMetaInfo("NOT NULL", 3));
        }

        return columnMetaInfos;
    }

}
