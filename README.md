# jpa-query-builder

## Class Diagram

```mermaid
classDiagram
    class EntityDefinitionBuilder {
        - EntityMetadata EntityMetadata
        + EntityDefinitionBuilder(type: Class<?>, dialect: Dialect)
    }
    class EntityManipulationBuilder {
        - EntityMetadata EntityMetadata
        + EntityManipulationBuilder(type: Class<?>, dialect: Dialect)
    }
    class EntityMetadata {
        - TableMetadataExtractor tableMetaDataExtractor
        - FieldMetadataExtractors fieldMetaDatas
        - Dialect dialect
        + EntityMetadata(type: Class<?>, dialect: Dialect)
    }
    class TableMetadataExtractor {
        - Class<?> type
        + TableMetadataExtractor(type: Class<?>)
    }
    class FieldMetadataExtractors {
        - List<FieldMetadataExtractor> fieldMetadataExtractorList
        + FieldMetadataExtractors(type: Class<?>)
    }
    class Dialect {
    }
    class H2Dialect {
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
        - Dialect dialect
        + AnnotationHandler(field: Field, annotationType: Class<T>, , dialect: Dialect)
        + metaInfos(): List<ColumnOption>
    }
    class ColumnAnnotationHandler {
        + ColumnAnnotationHandler(field: Field, , dialect: Dialect)
        + metaInfos(): List<ColumnOption>
    }
    class GeneratedValueAnnotationHandler {
        + GeneratedValueAnnotationHandler(field: Field, dialect: Dialect)
        + metaInfos(): List<ColumnOption>
    }
    class IdAnnotationHandler {
        + IdAnnotationHandler(field: Field, dialect: Dialect)
        + metaInfos(): List<ColumnOption>
    }
    
    EntityDefinitionBuilder --* EntityMetadata
    EntityManipulationBuilder --* EntityMetadata
    EntityMetadata --* TableMetadataExtractor
    EntityMetadata --* FieldMetadataExtractors
    EntityMetadata --* Dialect
    Dialect <|-- H2Dialect : extends
    FieldMetadataExtractors --* FieldMetadataExtractor
    FieldMetadataExtractor --|> ColumnOptionFactory : uses
    ColumnOptionFactory --* AnnotationHandler : uses
    AnnotationHandler --* Dialect : uses
    AnnotationHandler <|-- ColumnAnnotationHandler : implements
    AnnotationHandler <|-- GeneratedValueAnnotationHandler : implements
    AnnotationHandler <|-- IdAnnotationHandler : implements
```
