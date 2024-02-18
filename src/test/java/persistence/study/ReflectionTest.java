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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.fail;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        String carClassName = carClass.getName();

        assertThat(carClassName).isEqualTo("persistence.study.Car");
    }

    @Test
    @DisplayName("Car 클래스에서 test로 시작하는 메소드만 실행하기")
    void toMethodRun() throws Exception {
        Car car = carClass.getDeclaredConstructor()
                .newInstance();

        Method[] declaredMethods = carClass.getDeclaredMethods();
        List<Object> result = Arrays.stream(declaredMethods)
                .filter(declaredMethod -> {
                    String declaredMethodName = declaredMethod.getName();
                    return declaredMethodName.startsWith("test");
                })
                .map(declaredTestMethod -> invokeMethod(declaredTestMethod, car))
                .collect(Collectors.toList());

        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(2);
            softly.assertThat(result).contains("test : null", "test : 0");
        });
    }

    @Test
    @DisplayName("Car 클래스에서 @PrintView 애노테이션으로 설정되어있는 메소드만 실행하기")
    void testAnnotationMethodRun() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        Car car = carClass.getDeclaredConstructor()
                .newInstance();

        Method[] declaredMethods = carClass.getDeclaredMethods();
        Arrays.stream(declaredMethods)
                .filter(declaredMethod -> declaredMethod.isAnnotationPresent(PrintView.class))
                .forEach(declaredPrintViewMethod -> invokeMethod(declaredPrintViewMethod, car));

        String output = outputStreamCaptor.toString().trim();
        assertThat("자동차 정보를 출력 합니다.").isEqualTo(output);
    }

    private Object invokeMethod(Method declaredTestMethod, Car car) {
        try {
            return declaredTestMethod.invoke(car);
        } catch (IllegalAccessException | InvocationTargetException e) {
            fail();
        }
        return null;
    }

    @Test
    @DisplayName("Car 클래스의 private field에 값 할당 후 확인하기")
    void privateFieldAccess() throws Exception {
        Car car = carClass.getDeclaredConstructor()
                .newInstance();

        Arrays.stream(carClass.getDeclaredFields())
                .forEach(field -> {
                    String fieldName = field.getName();
                    Object fieldValue = getFieldValueByName(fieldName);
                    setFieldValue(field, car, fieldValue);
                });

        String carName = car.getName();
        int carPrice = car.getPrice();

        assertSoftly(softly -> {
            softly.assertThat(carName).isEqualTo("소나타");
            softly.assertThat(carPrice).isEqualTo(10_000);
        });
    }

    private Object getFieldValueByName(String name) {
        if (Objects.equals(name, "name")) {
            return "소나타";
        }
        if (Objects.equals(name, "price")) {
            return 10_000;
        }
        return null;
    }

    private void setFieldValue(Field field, Object instance, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            fail();
        }
    }

    @Test
    @DisplayName("인자를 가진 생성자를 찾아 인스턴스 생성하기")
    void constructorWithArgs() throws Exception {
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Constructor<?> findConstructor = Arrays.stream(constructors)
                .filter(constructor -> constructor.getParameterCount() == 2)
                .findAny()
                .orElseThrow();

        Car car = (Car) findConstructor.newInstance("소나타", 10_000);
        String carName = car.getName();
        int carPrice = car.getPrice();

        assertSoftly(softly -> {
            softly.assertThat(carName).isEqualTo("소나타");
            softly.assertThat(carPrice).isEqualTo(10_000);
        });
    }
}
