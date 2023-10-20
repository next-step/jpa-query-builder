# jpa-query-builder

## Class Diagram

```mermaid
classDiagram
    class EntityQueryBuilder {
        - EntityMetadata entityMetadata
        + EntityQueryBuilder(type: Class<?>)
    }
    class EntityMetadata {
        - TableInfo tableInfo
        - ColumnInfoCollection columnInfoCollection
        + EntityMetadata(type: Class<?>)
    }
    class TableInfo {
        - String name
        + TableInfo(type: Class<?>)
    }
    class ColumnInfoCollection {
        - List<GeneralColumnInfo> columnInfos
        + ColumnInfoCollection(type: Class<?>)
    }
    class GeneralColumnInfo {
        - String name
        - String dataType
        - List<ColumnMetaInfo> columnMetaInfos
        + ColumnInfo(field: Field)
    }
    class ColumnMetaInfoFactory {
        + createColumnMetaInfo(Field field): List<ColumnMetaInfo>
    }
    class ColumnMetaInfo {
        - String value
        - int priority
        + ColumnMetaInfo(annotation: Annotation)
    }
    class AnnotationInfo {
        # initialize(Field field)
        + getColumnMetaInfos(): List<ColumnMetaInfo>
    }
    class ColumnInfo {
        - Column column
        + initialize(Field field)
        + getColumnMetaInfos(): List<ColumnMetaInfo>

    }
    class GeneratedValueInfo {
        - GeneratedValue generatedValue
        + initialize(Field field)
        + getColumnMetaInfos(): List<ColumnMetaInfo>
    }
    class IdInfo {
        - Id id
        + initialize(Field field)
        + getColumnMetaInfos(): List<ColumnMetaInfo>
    }

    EntityQueryBuilder --* EntityMetadata
    EntityMetadata --* TableInfo
    EntityMetadata --* ColumnInfoCollection
    ColumnInfoCollection --* GeneralColumnInfo
    GeneralColumnInfo --|> ColumnMetaInfoFactory : uses
    GeneralColumnInfo --* ColumnMetaInfo : has
    ColumnMetaInfoFactory --* ColumnMetaInfo : returns
    AnnotationInfo <|-- ColumnInfo : implements
    AnnotationInfo <|-- GeneratedValueInfo : implements
    AnnotationInfo <|-- IdInfo : implements
    ColumnInfo --* ColumnMetaInfo : has
    GeneratedValueInfo --* ColumnMetaInfo : has
    IdInfo --* ColumnMetaInfo : has
```
