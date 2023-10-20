package persistence.sql.common;

import persistence.sql.ddl.Column;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnUtils {
	public static List<Column> convertClassToColumnList(Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
				.map(Column::new)
				.collect(Collectors.toList());
	}
}
