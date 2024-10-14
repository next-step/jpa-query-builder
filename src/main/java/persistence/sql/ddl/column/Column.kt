package persistence.sql.ddl.column

import jakarta.persistence.Transient
import parse.ParseType
import persistence.sql.find
import java.lang.reflect.Field

data class Column (
    private val columnParsers: Map<ParseType, ColumnParser>,
    private val field: Field
) {

    fun createQuery(): String {
        if (isTransientField()) {
            return ""
        }

        return listOf(
            columnParsers.find(ParseType.NAME).parse(field),
            columnParsers.find(ParseType.TYPE).parse(field),
            columnParsers.find(ParseType.NULLABLE).parse(field),
            columnParsers.find(ParseType.ID).parse(field))
        .filter { it.isNotEmpty() }
        .joinToString(" ")
    }

    private fun isTransientField(): Boolean {
        return field.isAnnotationPresent(Transient::class.java)
    }
}
