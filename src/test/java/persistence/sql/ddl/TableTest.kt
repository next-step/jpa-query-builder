package persistence.sql.ddl

import entity.Person
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class TableTest: DescribeSpec({

    describe("createQuery 메소드는") {

        context("클래스의 정보로") {

            val table = Table(Person::class.java)
            it ("테이블을 생성하는 쿼리를 반환한다") {
                val expect = "CREATE TABLE users (id bigint NOT NULL AUTO_INCREMENT , PRIMARY KEY (id),nick_name VARCHAR(255) DEFAULT NULL,old int DEFAULT NULL,email VARCHAR(255) NOT NULL)"

                table.createQuery() shouldBe expect
            }
        }
    }

    describe("dropQuery 메소드는") {

        context("클래스의 정보로") {

            val table = Table(Person::class.java)
            it("테이블을 제거하는 쿼리를 반환한다") {
                val expect = "DROP TABLE users"

                table.dropQuery() shouldBe expect
            }
        }
    }
})
