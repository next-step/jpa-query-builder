package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    @DisplayName("getSimpleName() 메서드는 클래스의 이름을 반환한다")
    @Test
    void getSimpleName() {
        Class<Car> carClass = Car.class;

        assertThat(carClass.getSimpleName()).isEqualTo("Car");
    }

    @DisplayName("getDeclaredFields() 메서드는 클래스에 선언된 필드를 반환한다")
    @Test
    void getDeclaredFields() {
        Class<Car> carClass = Car.class;
        List<String> fieldNames = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName)
                .toList();

        assertThat(fieldNames).containsExactly("name", "price");
    }

    @DisplayName("getDeclaredConstructor() 메서드는 클래스에 선언된 생성자를 반환한다")
    @Test
    void getDeclaredConstructor() {
        Class<Car> carClass = Car.class;
        List<String> constructorNames = Arrays.stream(carClass.getDeclaredConstructors())
                .map(Constructor::toString)
                .toList();

        assertThat(constructorNames).containsExactly(
                "public persistence.study.Car()",
                "public persistence.study.Car(java.lang.String,int)"
        );
    }

    @DisplayName("getDeclaredMethods() 메서드는 클래스에 선언된 메서드를 반환한다")
    @Test
    void getDeclaredMethods() {
        Class<Car> carClass = Car.class;
        List<String> methodNames = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::toString)
                .toList();

        assertThat(methodNames).containsExactly(
                "public void persistence.study.Car.printView()",
                "public java.lang.String persistence.study.Car.testGetName()",
                "public java.lang.String persistence.study.Car.testGetPrice()"
        );
    }
}
