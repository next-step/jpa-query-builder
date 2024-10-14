package persistence.sql.ddl

import jakarta.persistence.Table

class Table (
    private val clazz: Class<*>
) {
    private val simpleName = clazz.simpleName.lowercase()
    private val columns: List<Column> = extractColumn(clazz)

    fun createQuery(): String {
        return "CREATE TABLE ${extractTableName()} (${columnsToQuery()})"
    }

    fun dropQuery(): String {
        return "DROP TABLE ${extractTableName()}"
    }

    private fun extractTableName(): String {
        return this.clazz.getAnnotation(Table::class.java)?.name ?: this.simpleName
    }

    private fun columnsToQuery(): String = columns.map { it.createQuery() }
        .filter { it.isNotEmpty() }
        .joinToString(",")

    private fun extractColumn(clazz: Class<*>): List<Column> =
        clazz.declaredFields.map { Column(it) }

}
