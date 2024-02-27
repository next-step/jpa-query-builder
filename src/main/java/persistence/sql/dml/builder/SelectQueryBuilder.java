package persistence.sql.dml.builder;

import persistence.sql.dml.clause.Select;
import persistence.sql.dml.factory.SelectFactory;

public class SelectQueryBuilder {
    public String findAll(Class<?> clazz) {
        Select select = SelectFactory.getSelect(clazz);
        return String.format("select %s from %s",
                select.getColumns(),
                select.getTableName()
        );
    }

    public String findById(Class<?> clazz, Long id) {
        Select select = SelectFactory.getSelect(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append(findAll(clazz));
        sb.append(" where ");
        sb.append(select.getPKName());
        sb.append(" = ");
        sb.append(id.toString());
        return sb.toString();
    }
}
