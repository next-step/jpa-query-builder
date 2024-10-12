package persistence.sql.ddl

import exception.ColumnTypeUnavailableException
import jakarta.persistence.Id
import java.lang.reflect.Field
import java.util.StringJoiner

data class Column (
    private val field: Field
) {
    private val isIdColumn: Boolean = field.isAnnotationPresent(Id::class.java)

    fun toQuery(): String {
        return StringJoiner(" ").apply {
            this.add(field.name)
            this.add(columnType())
            this.add(nullable())
            if (isIdColumn) {
                this.add("AUTO INCREMENT")
                this.add(", PRIMARY KEY (${field.name})")
            }
        }.toString()
    }

    private fun columnType() =
        when (field.type) {
            String::class.java -> "VARCHAR(255)"
            Integer::class.java, Integer.TYPE -> "int"
            Long::class.java, java.lang.Long.TYPE -> "bigint"
            else -> throw ColumnTypeUnavailableException("사용할 수 없는 컬럼 타입입니다. [$field.type]")
        }

    private fun nullable(): String = if (isIdColumn) "NOT NULL" else "DEFAULT NULL"
}
