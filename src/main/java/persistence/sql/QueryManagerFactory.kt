package persistence.sql

import exception.ParserNotExistException
import parse.ParseConfig.COLUMN_NAME_PARSER
import parse.ParseConfig.COLUMN_NULLABLE_PARSER
import parse.ParseConfig.COLUMN_TYPE_PARSER
import parse.ParseConfig.ID_COLUMN_PARSER
import parse.ParseConfig.TABLE_NAME_PARSER
import parse.ParseType
import parse.ParseType.NAME
import parse.ParseType.TYPE
import parse.ParseType.NULLABLE
import parse.ParseType.ID
import persistence.sql.ddl.DefinitionQueryManager
import persistence.sql.ddl.column.Column
import persistence.sql.ddl.column.ColumnParser
import persistence.sql.ddl.table.Table
import persistence.sql.ddl.table.TableParser
import java.lang.reflect.Field

object QueryManagerFactory {

    private val TABLE_PARSERS = mapOf(
        NAME to TABLE_NAME_PARSER
    )

    private val COLUMN_PARSERS = mapOf(
        NAME to COLUMN_NAME_PARSER,
        TYPE to COLUMN_TYPE_PARSER,
        NULLABLE to COLUMN_NULLABLE_PARSER,
        ID to ID_COLUMN_PARSER
    )

    fun table(clazz: Class<*>): Table {
        return Table(TABLE_PARSERS, clazz)
    }

    fun column(field: Field): Column {
        return Column(COLUMN_PARSERS, field)
    }

    fun definition(vararg clazz: Class<*>): DefinitionQueryManager {
        return DefinitionQueryManager(
            TABLE_PARSERS,
            COLUMN_PARSERS,
            clazz.toList()
        )
    }
}

fun Map<ParseType, TableParser>.find(type: ParseType): TableParser {
    return this[type] ?: throw ParserNotExistException("TableParser 중 $type 에 해당하는 Parser가 존재하지 않습니다.")
}

fun Map<ParseType, ColumnParser>.find(type: ParseType): ColumnParser {
    return this[type] ?: throw ParserNotExistException("ColumnParser 중 $type 에 해당하는 Parser가 존재하지 않습니다.")
}
