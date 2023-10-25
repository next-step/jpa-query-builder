package persistence.meta;

import persistence.dialect.Dialect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetaDataColumns {
  private final List<MetaDataColumn> columns = new ArrayList<>();

  private MetaDataColumns(List<MetaDataColumn> metaColumns) {
    columns.addAll(metaColumns);
  }

  public static MetaDataColumns of(Class<?> clazz, Dialect dialect){
    List<MetaDataColumn> metaColumns = Arrays.stream(clazz.getDeclaredFields())
                    .map(field -> MetaDataColumn.of(field, dialect.convertToColumn(field)))
                    .collect(Collectors.toList());

    return new MetaDataColumns(metaColumns);
  }


  public String getColumns() {
    StringBuilder stringBuilder = new StringBuilder();
    for(MetaDataColumn dataColumn: columns){
      stringBuilder.append(dataColumn.getColumn());
    }
    return stringBuilder.toString();
  }
}
