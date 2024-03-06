package persistence.sql.ddl.translator;

import persistence.sql.ddl.ClassComponentType;
import persistence.sql.ddl.dto.javaclass.ClassName;
import persistence.sql.ddl.dto.db.TableName;

public class ClassNameTranslator implements ClassComponentTranslator<ClassName, TableName> {

    @Override
    public TableName invoke(ClassName className) {
        return new TableName(className.getTableName());
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_NAME;
    }
}
