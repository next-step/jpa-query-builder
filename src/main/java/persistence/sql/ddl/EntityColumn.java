package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.List;
import persistence.sql.ddl.metadata.EntityFieldType;
import persistence.sql.ddl.metadata.EntityMetaData;
import persistence.sql.ddl.metadata.H2EntityFieldType;
import persistence.sql.ddl.metadata.H2EntityGeneratedValueMetaData;
import persistence.sql.ddl.metadata.H2EntityIdMetaData;
import persistence.sql.ddl.metadata.H2EntityTypeMetaData;

public class EntityColumn {

	private final Field field;
	private static final EntityFieldType entityFieldType = new H2EntityFieldType();
	private static final List<EntityMetaData> metaDataList = List.of(
		new H2EntityGeneratedValueMetaData(),
		new H2EntityIdMetaData(),
		new H2EntityTypeMetaData()
	);

	public EntityColumn(Field field) {
		this.field = field;
	}

	public String getColumnQuery() {
		String columnName = getColumnName().concat(entityFieldType.getDataType(field));
		return metaDataList.stream()
			.map(it -> it.getMetaDateQuery(field))
			.reduce(columnName, String::concat);
	}

	private String getColumnName() {
		Column column = field.getAnnotation(Column.class);
		if (column == null || column.name().isEmpty()) {
			return field.getName() + " ";
		}
		return column.name() + " ";
	}

	public boolean hasId() {
		return field.isAnnotationPresent(Id.class);
	}
}
