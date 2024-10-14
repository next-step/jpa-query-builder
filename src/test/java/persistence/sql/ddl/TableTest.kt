package persistence.sql.ddl

import entity.Person
import entity.Person1
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import parse.ParseConfig
import persistence.sql.QueryManagerFactory
import persistence.sql.ddl.table.Table

class TableTest: DescribeSpec({

    describe("createQuery 메소드는") {

        context("클래스의 Table 어노테이션에 지정된 이름으로") {

            val table = QueryManagerFactory.table(Person::class.java)
            it ("테이블을 생성하는 쿼리를 반환한다") {

                table.createQuery() shouldBe "CREATE TABLE users"
            }
        }

        context("클래스에 Table 어노테이션이 존재하지 않는다면") {

            val table = QueryManagerFactory.table(Person1::class.java)
            it ("클래스 이름으로 테이블을 생성하는 쿼리를 반환한다") {

                table.createQuery() shouldBe "CREATE TABLE person1"
            }
        }
    }

    describe("dropQuery 메소드는") {

        context("클래스의 Table 어노테이션에 지정된 이름으로") {

            val table = QueryManagerFactory.table(Person::class.java)
            it("테이블을 제거하는 쿼리를 반환한다") {
                val expect = "DROP TABLE users"

                table.dropQuery() shouldBe expect
            }
        }

        context("클래스에 Table 어노테이션이 존재하지 않는다면") {

            val table = QueryManagerFactory.table(Person1::class.java)
            it ("클래스 이름으로 테이블을 제거하는 쿼리를 반환한다") {

                table.dropQuery() shouldBe "DROP TABLE person1"
            }
        }
    }
})
