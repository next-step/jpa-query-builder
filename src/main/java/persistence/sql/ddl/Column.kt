package persistence.sql.ddl

import exception.ColumnTypeUnavailableException
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.Transient
import java.lang.reflect.Field
import java.util.StringJoiner

data class Column (
    private val field: Field
) {
    private val isIdColumn: Boolean = field.isAnnotationPresent(Id::class.java)

    fun toQuery(): String {
        if (isTransientField()) return ""

        return StringJoiner(" ").apply {
            this.add(fieldName())
            this.add(columnType())
            this.add(nullable())
            if (isIdColumn) {
                this.add("AUTO_INCREMENT")
                this.add(", PRIMARY KEY (${field.name})")
            }
        }.toString()
    }

    private fun isTransientField(): Boolean = field.isAnnotationPresent(Transient::class.java)

    private fun fieldName(): String =
        if (field.isAnnotationPresent(Column::class.java)) {
            field.getAnnotation(Column::class.java).name.ifEmpty { field.name }
        } else {
            field.name
        }

    private fun columnType() =
        when (field.type) {
            String::class.java -> "VARCHAR(255)"
            Integer::class.java, Integer.TYPE -> "int"
            Long::class.java, java.lang.Long.TYPE -> "bigint"
            else -> throw ColumnTypeUnavailableException("사용할 수 없는 컬럼 타입입니다. [$field.type]")
        }

    private fun nullable(): String =
        if ((field.isAnnotationPresent(Column::class.java) && !field.getAnnotation(Column::class.java).nullable) || field.isAnnotationPresent(Id::class.java)) {
            "NOT NULL"
        } else {
            "DEFAULT NULL"
        }
}
