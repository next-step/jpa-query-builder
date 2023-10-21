package persistence.sql.dml;

import persistence.common.EntityClazz;
import persistence.common.FieldClazzList;

import java.util.Iterator;
import java.util.List;

public class DeleteQueryBuilder {

    public String deleteById(Class<?> clazz, List<Object> idList) {
        EntityClazz entityClazz = new EntityClazz(clazz);
        StringBuilder sb = new StringBuilder()
                .append("DELETE FROM ")
                .append(entityClazz.getName() + " ")
                .append("WHERE ");

        Iterator<Object> idIter = idList.iterator();
        new FieldClazzList(clazz).getIdFieldList()
                .forEach(fc -> sb.append(fc.getName())
                        .append("=")
                        .append(idIter.next()));

        return sb.append(";").toString();
    }
}
