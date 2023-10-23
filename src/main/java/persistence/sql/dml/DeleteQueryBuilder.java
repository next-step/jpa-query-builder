package persistence.sql.dml;

import persistence.common.EntityClazz;
import persistence.common.FieldClazz;
import persistence.common.FieldClazzList;
import persistence.common.FieldValueList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

    public String delete(Object entity) {
        List<Object> idList = new FieldClazzList(entity.getClass())
                .getIdFieldList()
                .stream()
                .map(fc -> fc.get(entity))
                .collect(Collectors.toList());

        return deleteById(entity.getClass(), idList);
    }
}
