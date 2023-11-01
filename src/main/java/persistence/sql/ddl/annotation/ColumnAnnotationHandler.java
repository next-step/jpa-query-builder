package persistence.sql.ddl.annotation;

import jakarta.persistence.Column;
import persistence.sql.ddl.ColumnOption;
import persistence.sql.ddl.scheme.ColumnSchemes;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.scheme.Schemes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ColumnAnnotationHandler extends AnnotationHandler<Column> {

    public ColumnAnnotationHandler(Field field, Dialect dialect) {
        super(field, Column.class, dialect);
    }

    @Override
    public List<ColumnOption> metaInfos() {

        List<ColumnOption> result = new ArrayList<>();
        ColumnSchemes schemes = dialect.getSchemes(Schemes.Column);

        if (!annotation.nullable()) {
            // TODO columnOption 부분 리팩토링
            ColumnOption columnOption = ColumnOption.valueOf(schemes.getScheme("nullable"));
            result.add(columnOption);
        }

        return result;
    }

}
