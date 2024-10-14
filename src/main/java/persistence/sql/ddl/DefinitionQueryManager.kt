package persistence.sql.ddl

import jakarta.persistence.Entity
import parse.ParseType
import persistence.sql.ddl.column.Column
import persistence.sql.ddl.column.ColumnParser
import persistence.sql.ddl.table.Table
import persistence.sql.ddl.table.TableParser

class DefinitionQueryManager (
    private val tableParsers: Map<ParseType, TableParser>,
    private val columnParsers: Map<ParseType, ColumnParser>,
    private val classes: List<Class<*>>
) {
    private val entities = classes.filter { it.isAnnotationPresent(Entity::class.java) }

    init {
        require(classes.size == entities.size) {
            val unavailableClass = classes.asSequence()
                .filter { !it.isAnnotationPresent(Entity::class.java) }
                .map { it.canonicalName }
                .take(4)
                .joinToString(limit = 3)
            "Entity 어노테이션을 작성해주세요. [$unavailableClass]"
        }
    }

    fun createQuery(): List<String> {
        return entities.map {
            val table = Table(tableParsers, it).createQuery()
            val columns = columnsToQuery(extractColumn(it))
            "$table ($columns)"
        }
    }

    fun dropQuery(): List<String> {
        return entities.map { Table(tableParsers, it).dropQuery() }
    }

    private fun columnsToQuery(columns: List<Column>): String {
        return columns.map { it.createQuery() }
            .filter { it.isNotEmpty() }
            .joinToString(",")
    }

    private fun extractColumn(clazz: Class<*>): List<Column> {
        return clazz.declaredFields.map { Column(columnParsers, it) }
    }
}
