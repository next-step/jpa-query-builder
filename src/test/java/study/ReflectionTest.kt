package study

import entity.Person
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.assertj.core.api.Assertions.*
import org.assertj.core.api.SoftAssertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ReflectionTest {

    @Test
    @DisplayName("클래스의 필드 정보를 확인한다")
    fun fields() {
        val clazz = Car::class.java
        assertSoftly {
            it.assertThat(clazz.name).isEqualTo("study.Car")

            it.assertThat(clazz.fields).isEmpty()
            it.assertThat(clazz.getDeclaredField("name").type).isEqualTo(String::class.java)
            it.assertThat(clazz.getDeclaredField("price").type).isEqualTo(Integer.TYPE)
        }
    }

    @Test
    @DisplayName("클래스의 생성자를 확인한다")
    fun constructors() {
        val clazz = Car::class.java

        assertSoftly {
            it.assertThat(clazz.constructors.size).isEqualTo(clazz.declaredConstructors.size)
            it.assertThat(clazz.getDeclaredConstructor().parameterCount).isEqualTo(0)
            it.assertThat(clazz.getDeclaredConstructor(String::class.java, Integer.TYPE).parameterCount).isEqualTo(2)
        }
    }

    @Test
    @DisplayName("클래스의 메서드를 확인한다")
    fun methods() {
        val clazz = Car::class.java

        assertSoftly {
            it.assertThat(clazz.methods.size).isEqualTo(12)
            it.assertThat(clazz.declaredMethods.size).isEqualTo(3)
            it.assertThat(clazz.getDeclaredMethod("printView").isAnnotationPresent(PrintView::class.java)).isTrue
            it.assertThat(clazz.getDeclaredMethod("testGetName").returnType).isEqualTo(String::class.java)
            it.assertThat(clazz.getDeclaredMethod("testGetPrice").returnType).isEqualTo(String::class.java)
        }
    }


    @Test
    @DisplayName("test로 시작하는 메소드를 실행한다")
    fun testMethodRun() {
        val clazz = Car::class.java
        val car = clazz.getDeclaredConstructor().newInstance()
        val expect = mapOf(
            "testGetName" to "test : ",
            "testGetPrice" to "test : 0"
        )

        clazz.declaredMethods
            .filter { it.name.startsWith("test") }
            .forEach {
                assertThat(it.invoke(car)).isEqualTo(expect[it.name])
            }
    }

    @Test
    @DisplayName("PrintView 어노테이션이 작성된 메서드를 실행한다")
    fun testAnnotationMethodRun() {
        val clazz = Car::class.java
        val car = clazz.getDeclaredConstructor().newInstance()

        val method = clazz.declaredMethods.find { it.isAnnotationPresent(PrintView::class.java) }
            ?: fail("메서드가 존재하지 않는다면 테스트에 실패합니다.")

        method.invoke(car)
    }

    @Test
    @DisplayName("private final field에 값을 할당한다")
    fun privateFieldAccess() {
        val clazz = Car::class.java
        val car = clazz.getDeclaredConstructor().newInstance()

        assertSoftly {
            it.assertThat(car.testGetName()).isEqualTo("test : ")
            it.assertThat(car.testGetPrice()).isEqualTo("test : 0")

            assertThatThrownBy { clazz.getDeclaredField("name").set(car, "업데이트") }
                .isInstanceOf(IllegalAccessException::class.java)
        }

        clazz.getDeclaredField("name").also {
            it.trySetAccessible()
            it.set(car, "업데이트")
        }
        clazz.getDeclaredField("price").also {
            it.trySetAccessible()
            it.set(car, 10000)
        }

        assertSoftly {
            it.assertThat(car.testGetName()).isEqualTo("test : 업데이트")
            it.assertThat(car.testGetPrice()).isEqualTo("test : 10000")
        }
    }

    @Test
    @DisplayName("인자를 전달받는 생성자를 통해 객체를 생성한다")
    fun constructorWithArgs() {
        val clazz = Car::class.java
        val car = clazz.getConstructor(String::class.java, Integer.TYPE).newInstance("테스트", 10000)
        assertSoftly {
            it.assertThat(car.testGetName()).isEqualTo("test : 테스트")
            it.assertThat(car.testGetPrice()).isEqualTo("test : 10000")
        }
    }

    @Test
    @DisplayName("필드에 선언된 어노테이션의 값을 조회한다")
    fun annotationValue() {
        val clazz = Person::class.java

        val idField = clazz.getDeclaredField("id")
        val nameField = clazz.getDeclaredField("name")
        val emailField = clazz.getDeclaredField("email")

        assertSoftly {
            it.assertThat(idField.getAnnotation(GeneratedValue::class.java).strategy).isEqualTo(GenerationType.IDENTITY)
            it.assertThat(nameField.getAnnotation(Column::class.java).name).isEqualTo("nick_name")
            it.assertThat(emailField.getAnnotation(Column::class.java).nullable).isFalse
        }
    }
}
