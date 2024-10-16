package persistence.sql.extract;

import static persistence.sql.extract.AnnotationPropertyExtractor.isPresent;
import static persistence.sql.extract.AnnotationPropertyExtractor.isNotPresent;
import static persistence.sql.extract.AnnotationPropertyExtractor.isNotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.ddl.exception.NotExistException;



public class TablePropertyExtractor implements AnnotationPropertyExtractor {

    public static String getName(Class<?> clazz) {
        validateEntityAnnotation(clazz);

        if (isTableNamePresent(clazz)) {
            return getTableName(clazz);
        }

        return getEntityNameOrSimpleName(clazz);
    }

    private static void validateEntityAnnotation(Class<?> clazz) {
        if (isNotPresent(clazz, Entity.class)) {
            throw new NotExistException("@Entity annotation. class = " + clazz.getName());
        }
    }

    private static boolean isTableNamePresent(Class<?> clazz) {
        return isPresent(clazz, Table.class) && isNotBlank(getTableName(clazz));
    }

    private static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        return table.name();
    }

    private static String getEntityNameOrSimpleName(Class<?> clazz) {
        Entity entity = clazz.getAnnotation(Entity.class);
        if (isNotBlank(entity.name())) {
            return entity.name();
        }
        return clazz.getSimpleName();
    }

}
