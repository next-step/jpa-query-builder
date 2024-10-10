package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    @Test
    @DisplayName("Car 객체의 모든 필드 정보 출력하기")
    void getDeclaredFields() {
        Class<Car> carClass = Car.class;
        List<String> fieldNames = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName)
                .toList();
        assertThat(fieldNames).contains("name", "price");
    }

    @Test
    @DisplayName("Car 객체의 모든 생성자 정보 출력하기")
    void getDeclaredConstructors() {
        Class<Car> carClass = Car.class;
        Constructor<?>[] declaredConstructors = carClass.getDeclaredConstructors();
        List<String> constructorNames = Arrays.stream(declaredConstructors)
                .map(Constructor::toString)
                .toList();

        assertThat(constructorNames).contains("public study.Car()", "public study.Car(java.lang.String,int)");
    }

    @Test
    @DisplayName("Car 객체의 모든 메서드 정보 출력하기")
    void getDeclaredMethods() {
        Class<Car> carClass = Car.class;
        List<String> methodNames = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::toString)
                .toList();
        assertThat(methodNames).contains(
                "public void study.Car.printView()",
                "public java.lang.String study.Car.testGetName()",
                "public java.lang.String study.Car.testGetPrice()"
        );
    }

}
