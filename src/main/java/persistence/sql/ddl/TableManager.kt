package persistence.sql.ddl

import jakarta.persistence.Entity

class TableManager (
    private val classes: List<Class<*>>
) {
    private val entities = classes.filter { it.isAnnotationPresent(Entity::class.java) }
    constructor(vararg clazz: Class<*>): this(clazz.toList())

    fun createQuery() = entities.map { Table(it).createQuery() }

    fun dropQuery() : List<String> = entities.map { Table(it).dropQuery() }
}
