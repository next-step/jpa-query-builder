package persistence.sql.ddl

import jakarta.persistence.Entity

class TableManager (
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
    constructor(vararg clazz: Class<*>): this(clazz.toList())

    fun createQuery(): List<String> {
        return entities.map { Table(it).createQuery() }
    }

    fun dropQuery(): List<String> {
        return entities.map { Table(it).dropQuery() }
    }
}
