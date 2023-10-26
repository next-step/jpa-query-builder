package persistence.meta;

import jakarta.persistence.Transient;
import persistence.dialect.Dialect;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetaDataColumns {

  public static final String DELIMITER = ",";
  private final List<MetaDataColumn> columns = new ArrayList<>();
  private MetaDataColumns(List<MetaDataColumn> metaColumns) {
    columns.addAll(metaColumns);
  }

  public static MetaDataColumns of(Class<?> clazz, Dialect dialect){
    List<MetaDataColumn> metaColumns = Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> isNotTransient(List.of(field.getAnnotations())))
            .map(field -> MetaDataColumn.of(field, dialect.convertToColumn(field)))
            .collect(Collectors.toList());

    return new MetaDataColumns(metaColumns);
  }

  public String getColumns() {
    return columns.stream()
            .map(MetaDataColumn::getColumn)
            .collect(Collectors.joining(DELIMITER));
  }

  private static boolean isNotTransient(List<Annotation> annotations) {
    return !annotations.stream()
            .anyMatch(annotation -> annotation.annotationType().equals(Transient.class));
  }

  public List<String> getSimpleColumns(){
    return columns.stream()
            .filter(MetaDataColumn::isNotPrimaryKey)
            .map(MetaDataColumn::getSimpleName)
            .collect(Collectors.toList());
  }
//            .collect(Collectors.joining(DELIMITER));

  public Map<String, String> getFieldToDBColumnMap(){
    return columns.stream()
            .collect(Collectors.toMap(
                    MetaDataColumn::getSimpleName,
                    MetaDataColumn::getFieldName
            ));
  }
}
