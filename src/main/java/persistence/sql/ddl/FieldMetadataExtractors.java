package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldMetadataExtractors {

    List<FieldMetadataExtractor> fieldMetadataExtractorList;

    public FieldMetadataExtractors(Class<?> type) {
        fieldMetadataExtractorList = Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(FieldMetadataExtractor::new)
                .collect(Collectors.toList());
    }

    public String getDefinition() {
        return fieldMetadataExtractorList.stream()
                .map(FieldMetadataExtractor::getDefinition)
                .collect(Collectors.joining(","));
    }


}
