package persistence.sql.ddl.annotation;

import jakarta.persistence.Column;
import persistence.sql.ddl.ColumnOption;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColumnInfo extends AnnotationInfo {

    public ColumnInfo(Field field) {
        super(field);
    }

    @Override
    protected Class<Column> getAnnotationType() {
        return Column.class;
    }

    @Override
    public List<ColumnOption> metaInfos() {
        Column column = (Column) super.annotation;

        List<ColumnOption> result = new ArrayList<>();
        if (!column.nullable()) {
            result.add(ColumnOption.NOT_NULL);
        }

        return result;
    }

}
