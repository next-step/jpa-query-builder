package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("Reflection 테스트")
class ReflectionTest {

    private static final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("클래스 정보를 확인할 수 있다.")
    void showClass() {
        final String carClassInfo = "persistence.study.Car";
        assertThat(carClass.getName()).isEqualTo(carClassInfo);
    }

    @DisplayName("Method 이름에 문자열이 들어간 메서드를 선택적으로 실행할 수 있다.")
    @ParameterizedTest(name = "\"{0}\" 문자열이 들어간 메서드만 실행할 수 있다.")
    @ValueSource(strings = {"test"})
    void testMethodRun(String containWord) {
        //given
        final List<Method> testStartWithMethodList = Arrays.stream(carClass.getDeclaredMethods())
            .filter(method -> method.getName().startsWith(containWord))
            .collect(Collectors.toList());

        //when
        final List<Object> invokedResultList = testStartWithMethodList.stream()
            .map((Method method) -> tryMethodInvoke(method, carClass))
            .collect(Collectors.toList());

        //then
        assertThat(invokedResultList).contains("test : null", "test : 0");
    }

    @ParameterizedTest(name = "\"{0}\" 어노테이션이 적용된 메서드만 실행할 수 있다.")
    @ValueSource(classes = {PrintView.class})
    @DisplayName("클래스가 소유한 Method 중 어노테이션이 적용된 메서드를 선택적으로 실행할 수 있다.")
    void findPrintViewAnnotation(Class<? extends Annotation> containAnnotation) {
        //given
        ByteArrayOutputStream captureOutputConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOutputConsole));

        //when
        final List<Method> testStartWithMethodList = Arrays.stream(carClass.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(containAnnotation))
            .collect(Collectors.toList());

        testStartWithMethodList
            .forEach((Method method) -> tryMethodInvoke(method, carClass));

        //then
        assertThat(captureOutputConsole.toString().trim()).isEqualTo("자동차 정보를 출력 합니다.");
    }

    @Test
    @DisplayName("객체의 Private Field에 값을 할당할 수 있다.")
    void setPrivateField() throws Exception {
        //given
        final String carName = "소나타";
        final int carPrice = 5500000;

        final Car car = carClass.getDeclaredConstructor().newInstance();

        final Field name = carClass.getDeclaredField("name");
        final Field price = carClass.getDeclaredField("price");

        name.setAccessible(true);
        price.setAccessible(true);

        //when
        name.set(car, carName);
        price.set(car, carPrice);

        //then
        assertAll(
            () -> assertThat(car.testGetName()).isEqualTo(String.format("test : %s", carName)),
            () -> assertThat(car.testGetPrice()).isEqualTo(String.format("test : %s", carPrice))
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스를 생성할 수 있다.")
    void constructorWithArgs() {
        //given
        final String carName = "소나타";
        final int carPrice = 100000;

        //when
        final Car car = (Car) Arrays.stream(carClass.getDeclaredConstructors())
            .filter(constructor -> constructor.getParameterCount() > 0)
            .map(constructor -> tryNewInstanceWithParameter(constructor, carName, carPrice))
            .findAny()
            .orElseThrow();

        //then
        assertAll(
            () -> assertThat(car.testGetName()).isEqualTo(String.format("test : %s", carName)),
            () -> assertThat(car.testGetPrice()).isEqualTo(String.format("test : %s", carPrice))
        );
    }

    private static Object tryMethodInvoke(Method method, Class<?> clazz) {
        try {
            return method.invoke(clazz.getDeclaredConstructor().newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object tryNewInstanceWithParameter(Constructor<?> constructor, String carName, int carPrice) {
        try {
            return constructor.newInstance(carName, carPrice);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}