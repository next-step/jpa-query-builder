package persistence.sql.ddl;

import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityColumns<T> {

	private final List<EntityColumn> columns;

	public EntityColumns(Class<T> aClass) {
		List<EntityColumn> inputColumns = Arrays.stream(aClass.getDeclaredFields())
			.filter(field -> !field.isAnnotationPresent(Transient.class))
			.map(EntityColumn::new)
			.collect(Collectors.toList());
		validatePrimaryId(inputColumns);
		this.columns = inputColumns;
	}

	public String getColumnQuery() {
		return this.columns.stream()
			.map(EntityColumn::getColumnQuery)
			.collect(Collectors.joining(","));
	}

	private void validatePrimaryId(List<EntityColumn> columns) {
		columns.stream()
			.filter(EntityColumn::hasId)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("primary key는 하나만 존재해야 합니다."));
	}
}
