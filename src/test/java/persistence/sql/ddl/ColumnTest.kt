package persistence.sql.ddl

import entity.Person
import exception.ColumnTypeUnavailableException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows

class ColumnTest: DescribeSpec({

    describe("toQuery 메소드는") {
        val clazz = Person::class.java

        context("지원하는 Field 타입이라면") {

            val idColumn = Column(clazz.getDeclaredField("id"))
            val stringColumn = Column(clazz.getDeclaredField("name"))
            val intColumn = Column(clazz.getDeclaredField("age"))
            it("DDL 컬럼 문자열을 반환한다.") {
                stringColumn.toQuery() shouldBe "name VARCHAR(255) DEFAULT NULL"
                intColumn.toQuery() shouldBe "age int DEFAULT NULL"
                idColumn.toQuery() shouldBe "id bigint NOT NULL AUTO INCREMENT , PRIMARY KEY (id)"
            }
        }

        context("지원하지 않는 Field 타입이라면") {
            val clazz = Table::class.java
            val unavailableColumn = Column(clazz.getDeclaredField("columns"))

            it("지원하지 않는 타입이라는 의미의 예외를 던진다.") {
                assertThrows<ColumnTypeUnavailableException> { unavailableColumn.toQuery() }
            }
        }
    }
})
