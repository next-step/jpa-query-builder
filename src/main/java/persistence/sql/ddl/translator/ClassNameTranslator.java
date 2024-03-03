package persistence.sql.ddl.translator;

import persistence.sql.ddl.ClassComponentType;
import persistence.sql.ddl.dto.javaclass.ClassName;
import persistence.sql.ddl.dto.db.TableName;

import java.util.List;
import java.util.stream.Collectors;

public class ClassNameTranslator implements ClassComponentTranslator<ClassName, TableName> {

    @Override
    public List<TableName> invoke(List<ClassName> classNames) {
        return classNames.stream()
                .map(name -> new TableName(name.getTableName()))
                .collect(Collectors.toList());
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_NAME;
    }
}
