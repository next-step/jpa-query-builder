package persistence.sql.entity;

import jakarta.persistence.Table;
import persistence.sql.dml.exception.NotFoundIdException;
import persistence.sql.entity.model.DomainType;
import persistence.sql.entity.model.DomainTypes;

import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class EntityMappingTable {

    private final String tableName;
    private final DomainTypes domainTypes;
    private final Class<?> clazz;

    public EntityMappingTable(final String tableName,
                              final DomainTypes domainTypes,
                              final Class<?> clazz) {
        this.tableName = tableName;
        this.domainTypes = domainTypes;
        this.clazz = clazz;
    }

    public static EntityMappingTable from(final Class<?> clazz) {
        return new EntityMappingTable(
                clazz.getSimpleName(),
                DomainTypes.from(clazz.getDeclaredFields()),
                clazz
        );
    }

    public String getTableName() {
        if (clazz.isAnnotationPresent(Table.class)) {
            return clazz.getAnnotation(Table.class).name();
        }

        return tableName;
    }

    public DomainTypes getDomainTypes() {
        return domainTypes;
    }

    public DomainType getPkDomainTypes() {
        Spliterator<DomainType> spliterator = domainTypes.spliterator();
        return StreamSupport.stream(spliterator, false)
                .filter(DomainType::isExistsId)
                .findFirst()
                .orElseThrow(NotFoundIdException::new);
    }

}
