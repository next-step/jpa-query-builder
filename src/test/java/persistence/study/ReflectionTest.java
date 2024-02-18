package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        final Class<Car> carClass = Car.class;
        // 패키지+클래스 이름
        assertThat(carClass.getName()).isEqualTo("persistence.study.Car");

        // 모든 필드 목록
        List<String> fields = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::toString)
                .toList();
        assertThat(fields).contains(
                "private java.lang.String persistence.study.Car.name",
                "private int persistence.study.Car.price"
        );

        // 모든 생성자 반환
        List<String> constructors = Arrays.stream(carClass.getDeclaredConstructors())
                .map(Constructor::toString)
                .toList();
        assertThat(constructors).contains(
                "public persistence.study.Car()",
                "public persistence.study.Car(java.lang.String,int)"
        );

        // 모든 메서드 반환
        List<String> methods = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::toString)
                .toList();
        assertThat(methods).contains(
                "public java.lang.String persistence.study.Car.getName()",
                "public void persistence.study.Car.printView()",
                "public int persistence.study.Car.getPrice()",
                "public java.lang.String persistence.study.Car.testGetName()",
                "public java.lang.String persistence.study.Car.testGetPrice()"
        );
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();

        for (final Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                final String result = (String) method.invoke(car);
                logger.debug(result);
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();

        List<Method> annotationMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .toList();

        for (final Method method : annotationMethods) {
            method.invoke(car);
            assertThat(outputStreamCaptor.toString()).isEqualTo("자동차 정보를 출력 합니다.\n");
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();
        final String name = "Sonata";
        final int price = 1000;

        for (final Field field : carClass.getDeclaredFields()) {
            if (field.getName().equals("name")) {
                field.setAccessible(true);
                field.set(car, name);
            }
            if (field.getName().equals("price")) {
                field.setAccessible(true);
                field.set(car, price);
            }
        }

        assertAll(
                () -> assertThat(car.getName()).isEqualTo(name),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final String name = "Sonata";
        final int price = 1000;

        final Car car = carClass.getConstructor(String.class, int.class).newInstance(name, price);

        assertAll(
                () -> assertThat(car).isInstanceOf(Car.class),
                () -> assertThat(car.getName()).isEqualTo(name),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }
}
