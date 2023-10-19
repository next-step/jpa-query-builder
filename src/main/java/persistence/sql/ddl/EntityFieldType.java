package persistence.sql.ddl;

import jakarta.persistence.Column;
import java.util.Arrays;

public enum EntityFieldType {

	BIG_INT("BIGINT", Long.class),
	INTEGER("INT", Integer.class),
	VAR_CHAR("VARCHAR(255)", String.class),
	;

	private final String dataType;
	private final Class<?> columnClass;

	EntityFieldType(String dataType, Class<?> javaColumnClass) {
		this.dataType = dataType;
		this.columnClass = javaColumnClass;
	}

	public static EntityFieldType find(final Class<?> columnClass) {
		return Arrays.stream(values())
			.filter(columnType -> columnType.columnClass == columnClass)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 컬럼 타입입니다."));
	}

	public String getDataType() {
		return dataType;
	}
}
