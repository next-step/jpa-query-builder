package persistence.sql.entity.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DomainTypes {

    private final List<DomainType> domainTypes;

    private DomainTypes(final List<DomainType> domainTypes) {
        this.domainTypes = domainTypes;
    }

    public List<DomainType> getDomainTypes() {
        return domainTypes;
    }

    public static DomainTypes from(Field[] fields) {
        return new DomainTypes(Arrays.stream(fields)
                .map(DomainType::from)
                .collect(Collectors.toList()));
    }
}
