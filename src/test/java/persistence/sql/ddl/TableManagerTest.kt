package persistence.sql.ddl

import entity.Person
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import persistence.Application

class TableManagerTest: DescribeSpec({

    describe("createQuery 메소드는") {
        context("Entity 어노테이션이 작성된 클래스들은") {
            val tableManager = TableManager(Person::class.java)
            it("테이블을 생성하는 쿼리를 반환한다.") {
                tableManager.createQuery() shouldBe listOf(
                    "CREATE TABLE users (id bigint NOT NULL AUTO_INCREMENT , PRIMARY KEY (id),nick_name VARCHAR(255) DEFAULT NULL,old int DEFAULT NULL,email VARCHAR(255) NOT NULL)"
                )
            }
        }

        context("Entity 어노테이션이 작성되어 있지 않은 클래스가 존재한다면") {
            it("TableManger 객체 생성에 실패하며, 잘못된 파라미터가 전달되었다는 의미의 예외를 던진다.") {
                shouldThrow<IllegalArgumentException> { TableManager(Application::class.java, TableManager::class.java, Table::class.java, Column::class.java) }
                    .message shouldBe "Entity 어노테이션을 작성해주세요. [persistence.Application, persistence.sql.ddl.TableManager, persistence.sql.ddl.Table, ...]"
            }
        }
    }
})
