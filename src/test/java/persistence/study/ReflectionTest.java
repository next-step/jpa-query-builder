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
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 모든 필드 정보 가지고 오기 ")
    void showClass() {
        Class<Car> carClass = Car.class;
        Field[] fields = carClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            logger.info("name: " + field.getName() + ", type: " + field.getType());
        });
    }

    @Test
    @DisplayName("Car 객체 생성자 정보 가지고 오기 ")
    void showClass_2() {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Arrays.stream(constructors).forEach(constructor -> {
            logger.info("Constructor: " + constructor.getName() + ", Parameter Types " + Arrays.toString(constructor.getParameterTypes()));
        });
    }

    @Test
    @DisplayName("Car 객체 메서드에 대한 정보 가지고 오기 ")
    void showClass_3() {
        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            logger.info("Method: " + method.getName() + ", Return Type: " + method.getReturnType());
        });
    }

    @Test
    @DisplayName("test로 시작하는 메서드 실행하기")
    void testMethodRun() throws Exception {
        Class<?> carClass = Car.class;
        Object carInstance = carClass.getDeclaredConstructor().newInstance(); // Car 인스턴스 생성
        Method[] methods = carClass.getDeclaredMethods(); // Car 클래스의 메서드들 가져오기

        Arrays.stream(methods)
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        logger.info("invoke: " + method.invoke(carInstance));
                        method.invoke(carInstance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메서드 실행 ")
    void annotationMethodRun() throws Exception {
        Class<?> carClass = Car.class;
        Object carInstance = carClass.getDeclaredConstructor().newInstance();
        Method[] methods = carClass.getDeclaredMethods();
        // 기존 System.out을 임시로 바꿀 스트림 생성

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent)); // System.out을 ByteArrayOutputStream으로 설정

        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        method.invoke(carInstance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
        // 메서드 호출 후 출력된 내용을 String으로 변환
        String output = outContent.toString().trim();
        logger.info("invoke: " + output);

        // assertThat으로 출력된 내용을 검증
        assertThat(output).isEqualTo("자동차 정보를 출력 합니다.");
    }

        @Test
        @DisplayName("private field 값 할당")
        void privateFieldAccess() throws Exception {
            Class<Car> carClass = Car.class;
            Car car = carClass.getDeclaredConstructor().newInstance();

            Field nameField = carClass.getDeclaredField("name");
            Field priceField = carClass.getDeclaredField("price");
            nameField.setAccessible(true);
            priceField.setAccessible(true);
            nameField.set(car, "Benz");
            priceField.set(car, 1000);

            assertThat(car.testGetName()).isEqualTo("test : Benz");
            assertThat(car.testGetPrice()).isEqualTo("test : 1000");
        }

        @Test
        @DisplayName("자바 Reflection API를 활용해 Car 인스턴스를 생성한다.")
        void createInstance() throws Exception {
            Class<Car> carClass = Car.class;
            Constructor<Car> constructor = carClass.getDeclaredConstructor(String.class, int.class);
            Car car = constructor.newInstance("Benz", 1000);

            assertThat(car.testGetName()).endsWith("Benz");
            assertThat(car.testGetPrice()).endsWith("1000");
        }
}
