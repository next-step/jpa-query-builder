package persistence.sql.ddl

import entity.Person
import exception.ColumnTypeUnavailableException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ColumnTest: DescribeSpec({

    describe("createQuery 메소드는") {
        val person = Person::class.java

        context("지원하는 Field 타입은") {

            val idColumn = Column(person.getDeclaredField("id"))
            val stringColumn = Column(person.getDeclaredField("name"))
            val intColumn = Column(person.getDeclaredField("age"))
            it("DDL 컬럼 문자열을 반환한다.") {
                stringColumn.createQuery() shouldBe "nick_name VARCHAR(255) DEFAULT NULL"
                intColumn.createQuery() shouldBe "old int DEFAULT NULL"
                idColumn.createQuery() shouldBe "id bigint NOT NULL AUTO_INCREMENT , PRIMARY KEY (id)"
            }
        }

        context("지원하지 않는 Field 타입은") {
            val table = Table::class.java
            val unavailableColumn = Column(table.getDeclaredField("columns"))

            it("지원하지 않는 타입이라는 의미의 예외를 던진다.") {
                shouldThrow<ColumnTypeUnavailableException> { unavailableColumn.createQuery() }
            }
        }

        context("Transient 어노테이션이 작성된 필드는") {
            val transientColumn = Column(person.getDeclaredField("index"))

            it("공백을 반환한다") {
                transientColumn.createQuery() shouldBe ""
            }
        }
    }
})
