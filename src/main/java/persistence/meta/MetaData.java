package persistence.meta;

import persistence.dialect.Dialect;

public class MetaData {
  private final MetaDataTable metaDataTable;
  private final MetaDataColumns metaDataColumns;
  private static final String CREATE_TABLE_CLAUSE = "%s (%s)";

  private MetaData(MetaDataTable metaDataTable, MetaDataColumns metaDataColumns) {
    this.metaDataTable = metaDataTable;
    this.metaDataColumns = metaDataColumns;
  }

  public static MetaData of(Class<?> clazz, Dialect dialect) {
    return new MetaData(MetaDataTable.of(clazz), MetaDataColumns.of(clazz, dialect));
  }

  public String getCreateClause() {
    return String.format(CREATE_TABLE_CLAUSE, metaDataTable.getName(), metaDataColumns.getColumns());
  }

  public String getDropClause() {
    return metaDataTable.getName();
  }
}

