package study

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ReflectionTest {

    @Test
    @DisplayName("클래스의 모든 필드, 생성자, 메서드 정보를 조회한다")
    fun showClass() {
        val clazz = Car::class.java

        assertThat(clazz.name).isEqualTo("study.Car")

        assertThat(clazz.fields).isEmpty()
        assertThat(clazz.declaredFields[0].name).isEqualTo("name")
        assertThat(clazz.declaredFields[1].name).isEqualTo("price")

        assertThat(clazz.constructors.size).isEqualTo(clazz.declaredConstructors.size)
        assertThat(clazz.getDeclaredConstructor().parameterCount).isEqualTo(0)
        assertThat(clazz.constructors[0].parameterCount).isEqualTo(0)
        assertThat(clazz.constructors[2].parameterCount).isEqualTo(2)

        assertThat(clazz.methods.size).isEqualTo(12)
        assertThat(clazz.declaredMethods.size).isEqualTo(3)
        assertThat(clazz.declaredMethods[0].name).isEqualTo("printView")
        assertThat(clazz.declaredMethods[1].name).isEqualTo("testGetName")
        assertThat(clazz.declaredMethods[2].name).isEqualTo("testGetPrice")
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

        assertThat(car.testGetName()).isEqualTo("test : ")
        assertThat(car.testGetPrice()).isEqualTo("test : 0")

        assertThatThrownBy { clazz.getDeclaredField("name").set(car, "업데이트") }
            .isInstanceOf(IllegalAccessException::class.java)

        clazz.getDeclaredField("name").also {
            it.trySetAccessible()
            it.set(car, "업데이트")
        }
        clazz.getDeclaredField("price").also {
            it.trySetAccessible()
            it.set(car, 10000)
        }
        assertThat(car.testGetName()).isEqualTo("test : 업데이트")
        assertThat(car.testGetPrice()).isEqualTo("test : 10000")
    }

    @Test
    @DisplayName("인자를 전달받는 생성자를 통해 객체를 생성한다")
    fun constructorWithArgs() {
        val clazz = Car::class.java
        val car = clazz.getConstructor(String::class.java, Integer.TYPE).newInstance("테스트", 10000)

        assertThat(car.testGetName()).isEqualTo("test : 테스트")
        assertThat(car.testGetPrice()).isEqualTo("test : 10000")
    }
}
