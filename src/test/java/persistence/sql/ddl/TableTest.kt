package persistence.sql.ddl

import entity.Person
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import parse.ParseConfig
import persistence.sql.QueryManagerFactory
import persistence.sql.ddl.table.Table

class TableTest: DescribeSpec({

    describe("createQuery 메소드는") {

        context("클래스의 정보로") {

            val table = QueryManagerFactory.table(Person::class.java)
            it ("테이블을 생성하는 쿼리를 반환한다") {
                val expect = "CREATE TABLE users"

                table.createQuery() shouldBe expect
            }
        }
    }

    describe("dropQuery 메소드는") {

        context("클래스의 정보로") {

            val table = QueryManagerFactory.table(Person::class.java)
            it("테이블을 제거하는 쿼리를 반환한다") {
                val expect = "DROP TABLE users"

                table.dropQuery() shouldBe expect
            }
        }
    }
})
