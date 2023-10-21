# jpa-query-builder

## Class Diagram

```mermaid
classDiagram
    class EntityQueryBuilder {
        - EntityMetadata EntityMetadata
        + EntityQueryBuilder(type: Class<?>)
    }
    class EntityMetadata {
        - TableMetadataExtractor tableMetaDataExtractor
        - FieldMetadataExtractors fieldMetaDatas
        + EntityMetadata(type: Class<?>)
    }
    class TableMetadataExtractor {
        - Class<?> type
        + TableMetadataExtractor(type: Class<?>)
    }
    class FieldMetadataExtractors {
        - List<FieldMetadataExtractor> fieldMetadataExtractorList
        + FieldMetadataExtractors(type: Class<?>)
    }
    class FieldMetadataExtractor {
        - Field field
        + FieldMetadataExtractor(field: Field)
    }
    class ColumnOptionFactory {
        + createColumnOption(Field field): String
    }
    class AnnotationHandler {
        - T annotation
        + AnnotationHandler(field: Field, annotationType: Class<T>)
        + metaInfos(): List<ColumnOption>
    }
    class ColumnAnnotationHandler {
        + ColumnAnnotationHandler(field: Field)
        + metaInfos(): List<ColumnOption>
    }
    class GeneratedValueAnnotationHandler {
        + GeneratedValueAnnotationHandler(field: Field)
        + metaInfos(): List<ColumnOption>
    }
    class IdAnnotationHandler {
        + IdAnnotationHandler(field: Field)
        + metaInfos(): List<ColumnOption>
    }
    
    EntityQueryBuilder --* EntityMetadata
    EntityMetadata --* TableMetadataExtractor
    EntityMetadata --* FieldMetadataExtractors
    FieldMetadataExtractors --* FieldMetadataExtractor
    FieldMetadataExtractor --|> ColumnOptionFactory : uses
    ColumnOptionFactory --* AnnotationHandler : uses
    AnnotationHandler <|-- ColumnAnnotationHandler : implements
    AnnotationHandler <|-- GeneratedValueAnnotationHandler : implements
    AnnotationHandler <|-- IdAnnotationHandler : implements
```
