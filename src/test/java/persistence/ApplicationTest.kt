package persistence

import database.DatabaseServer
import database.H2
import entity.Person
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import jdbc.JdbcTemplate
import persistence.sql.ddl.TableManager
class ApplicationTest : StringSpec({

    "존재하지 않는 테이블을 조회한다면 예외를 반환한다" {
        val server: DatabaseServer = H2()
        server.start()

        val jdbcTemplate = JdbcTemplate(server.connection)
        val tableManager = TableManager(Person::class.java)

        tableManager.createQuery().forEach(jdbcTemplate::execute)
        jdbcTemplate.execute("select * from users")

        tableManager.dropQuery().forEach(jdbcTemplate::execute)
        shouldThrow<RuntimeException> { jdbcTemplate.execute("select * from users") }
    }
})
