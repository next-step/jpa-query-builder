package persistence.meta;

import jakarta.persistence.Transient;
import persistence.dialect.Dialect;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetaDataColumns {
  private final List<MetaDataColumn> columns = new ArrayList<>();
  private static final String COMMA = ",";
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
    StringBuilder stringBuilder = new StringBuilder();
    for(MetaDataColumn dataColumn: columns){
      stringBuilder.append(dataColumn.getColumn());

      if (!dataColumn.equals(columns.get(columns.size()-1))){
        stringBuilder.append(COMMA);
      }
    }

    return stringBuilder.toString();
  }
  private static boolean isNotTransient(List<Annotation> annotations) {
    return !annotations.stream()
            .anyMatch(annotation -> annotation.annotationType().equals(Transient.class));
  }
}
