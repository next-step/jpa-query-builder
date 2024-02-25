package persistence.sql.column;

import java.lang.reflect.Field;

import persistence.sql.type.NameType;
import persistence.sql.type.NullableType;
import persistence.sql.dialect.Dialect;

public class GeneralColumn implements Column {

	private static final String DEFAULT_COLUMN_FORMAT = "%s %s";

	private final NameType name;
	private final ColumnType columnType;
	private final NullableType nullable;

	public GeneralColumn(Field field, Dialect dialect) {
		this.columnType = dialect.getColumn(field.getType());
		this.name = new NameType(field.getName());
		this.nullable = new NullableType();

		if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
			boolean isNullable = field.getAnnotation(jakarta.persistence.Column.class).nullable();
			this.nullable.update(isNullable);
			String columnName = field.getAnnotation(jakarta.persistence.Column.class).name();
			this.name.setColumnName(columnName);
		}
	}

	@Override
	public String getDefinition() {
		return String.format(DEFAULT_COLUMN_FORMAT, name.getValue(),
			columnType.getColumnDefinition() + nullable.getDefinition());
	}

	@Override
	public String getName() {
		return name.getValue();
	}

	@Override
	public String getFieldName() {
		return name.getFieldName();
	}

}
