package persistence.sql.dml;

import persistence.common.EntityClazz;
import persistence.common.FieldClazzList;

import java.util.Iterator;
import java.util.List;

public class SelectQueryBuilder {

    public String findAll(Class<?> clazz) {
        EntityClazz entityClazz = new EntityClazz(clazz);
        return "SELECT * FROM " + entityClazz.getName() + ";";
    }

    public String findById(Class<?> clazz, List<Object> ids) {
        EntityClazz entityClazz = new EntityClazz(clazz);
        FieldClazzList fieldClazzList = new FieldClazzList(clazz);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(entityClazz.getName());
        sb.append(" WHERE ");
        Iterator<Object> idIter = ids.iterator();
        fieldClazzList.getIdFieldList()
                .forEach(fc -> sb.append(fc.getName())
                        .append("=")
                        .append(idIter.next()));

        return sb.toString();
    }
}
