package persistence.sql.ddl

import entity.Person
import entity.Person1
import exception.ColumnTypeUnavailableException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import persistence.sql.QueryManagerFactory
import persistence.sql.ddl.table.Table

class ColumnTest: DescribeSpec({

    describe("createQuery 메소드는") {


        context("지원하는 Field 타입은") {
            val person = Person::class.java

            it("Column 어노테이션으로 지정된 name을 기준으로 DDL 컬럼 문자열을 반환한다") {
                val idColumn = QueryManagerFactory.column(person.getDeclaredField("id"))
                val stringColumn = QueryManagerFactory.column(person.getDeclaredField("name"))
                val intColumn = QueryManagerFactory.column(person.getDeclaredField("age"))

                stringColumn.createQuery() shouldBe "nick_name VARCHAR(255) DEFAULT NULL"
                intColumn.createQuery() shouldBe "old int DEFAULT NULL"
                idColumn.createQuery() shouldBe "id bigint NOT NULL AUTO_INCREMENT, PRIMARY KEY (id)"
            }

            val person1 = Person1::class.java

            it("Column 어노테이션이 존재하지 않는다면 필드 이름을 기준으로 DDL 컬럼 문자열을 반환한다") {
                val idColumn = QueryManagerFactory.column(person1.getDeclaredField("id"))
                val stringColumn = QueryManagerFactory.column(person1.getDeclaredField("name"))
                val intColumn = QueryManagerFactory.column(person1.getDeclaredField("age"))

                stringColumn.createQuery() shouldBe "name VARCHAR(255) DEFAULT NULL"
                intColumn.createQuery() shouldBe "age int DEFAULT NULL"
                idColumn.createQuery() shouldBe "id bigint NOT NULL AUTO_INCREMENT, PRIMARY KEY (id)"
            }
        }

        context("지원하지 않는 Field 타입은") {
            val table = Table::class.java
            val unavailableColumn = QueryManagerFactory.column(table.getDeclaredField("tableParsers"))

            it("지원하지 않는 타입이라는 의미의 예외를 던진다.") {
                shouldThrow<ColumnTypeUnavailableException> { unavailableColumn.createQuery() }
            }
        }

        context("Transient 어노테이션이 작성된 필드는") {
            val person = Person::class.java
            val transientColumn = QueryManagerFactory.column(person.getDeclaredField("index"))

            it("공백을 반환한다") {
                transientColumn.createQuery() shouldBe ""
            }
        }
    }
})
