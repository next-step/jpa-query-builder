package persistence.sql.ddl;

import jakarta.persistence.Transient;
import persistence.sql.ddl.dialect.Dialect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldMetadataExtractors {

    private final List<FieldMetadataExtractor> fieldMetadataExtractorList;

    public FieldMetadataExtractors(Class<?> type) {
        fieldMetadataExtractorList = Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(FieldMetadataExtractor::new)
                .collect(Collectors.toList());
    }

    public String getDefinition(Dialect dialect) {
        return fieldMetadataExtractorList.stream()
                .map(fieldMetadataExtractor -> fieldMetadataExtractor.getDefinition(dialect))
                .collect(Collectors.joining(","));
    }

    public String getColumnNames(Object entity) {
        return fieldMetadataExtractorList.stream()
                .map(FieldMetadataExtractor -> {
                    try {
                        return FieldMetadataExtractor.getColumnName(entity);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .filter(columnName -> !columnName.isEmpty())
                .collect(Collectors.joining(", "));
    }

    public String getColumnNames(Class<?> type) {
        return fieldMetadataExtractorList.stream()
                .map(FieldMetadataExtractor -> {
                    try {
                        return FieldMetadataExtractor.getColumnName(type);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .filter(columnName -> !columnName.isEmpty())
                .collect(Collectors.joining(", "));
    }

    public String getValueFrom(Object entity) {
        return fieldMetadataExtractorList.stream()
                .map(fieldMetadataExtractor -> {
                    try {
                        return fieldMetadataExtractor.getValueFrom(entity);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .filter(columnName -> !columnName.isEmpty())
                .collect(Collectors.joining(", "));
    }

    public String getIdColumnName(Class<?> type) {
        return fieldMetadataExtractorList.stream()
                .filter(FieldMetadataExtractor::isId)
                .map(fieldMetadataExtractor -> {
                    try {
                        return fieldMetadataExtractor.getColumnName(type);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No @Id annotation"));
    }
}
