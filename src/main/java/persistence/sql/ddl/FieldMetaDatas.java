package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldMetaDatas {

    List<FieldMetaData> fieldMetaDataList;

    public FieldMetaDatas(Class<?> type) {
        fieldMetaDataList = Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(FieldMetaData::new)
                .collect(Collectors.toList());
    }

    public String getDefinition() {
        return fieldMetaDataList.stream()
                .map(FieldMetaData::getDefinition)
                .collect(Collectors.joining(","));
    }


}
