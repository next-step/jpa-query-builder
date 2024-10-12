package persistence.sql.ddl

import entity.Person
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*

class TableManagerTest: DescribeSpec({

    describe("createQuery 메소드는") {
        context("Entity 어노테이션이 작성된 클래스들은") {
            val tableManager = TableManager(Person::class.java)
            it("테이블을 생성하는 쿼리를 반환한다.") {
                tableManager.createQuery() shouldBe listOf(
                    "CREATE TABLE person (id bigint NOT NULL AUTO_INCREMENT , PRIMARY KEY (id),name VARCHAR(255) DEFAULT NULL,age int DEFAULT NULL)"
                )
            }
        }

        context("Entity 어노테이션이 작성되어 있지 않다면") {
            val tableManager = TableManager(Column::class.java)
            it("빈 리스트를 반환한다.") {
                tableManager.createQuery() shouldBe listOf()
            }
        }
    }
})
