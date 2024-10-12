package persistence.sql.ddl

class Table (
    private val clazz: Class<*>
) {
    private val columns: List<Column> = extractColumn(clazz)

    fun toQuery(): String {
        val tableName = this.clazz.simpleName.lowercase()
        return "CREATE TABLE $tableName (${columnsToQuery()})"
    }

    private fun columnsToQuery(): String = columns.map { it.toQuery() }
        .filter { it.isNotEmpty() }
        .joinToString(",")

    private fun extractColumn(clazz: Class<*>): List<Column> =
        clazz.declaredFields.map { Column(it) }
}
