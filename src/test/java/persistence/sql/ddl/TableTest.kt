package persistence.sql.ddl

import entity.Person
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class TableTest: DescribeSpec({

    describe("toQuery 메소드는") {

        context("클래스의 정보를") {

            val table = Table(Person::class.java)
            it ("DDL 쿼리로 변환한다.") {
                val expect = "CREATE TABLE person (id bigint NOT NULL AUTO_INCREMENT , PRIMARY KEY (id),nick_name VARCHAR(255) DEFAULT NULL,old int DEFAULT NULL,email VARCHAR(255) NOT NULL)"

                table.toQuery() shouldBe expect
            }
        }
    }
})
