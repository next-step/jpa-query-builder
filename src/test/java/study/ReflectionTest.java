package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    @Test
    @DisplayName("test로 시작하는 메서드를 실행한다")
    void invokeTest() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();

        List<String> invokes = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> {
                    try {
                        return (String) method.invoke(car);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        assertThat(invokes).hasSize(2);
        assertThat(invokes).allMatch(o -> o.startsWith("test : "));

    }


    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행한다")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();

        Method annotationMethod = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .findFirst()
                .orElseThrow();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        annotationMethod.invoke(car);
        assertThat(outputStream.toString()).isEqualTo("자동차 정보를 출력 합니다.\n");

    }


}
