package persistence.study;

import annotation.PrintView;
import domain.step1.Car;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReflectionTest {

    private static final Class<Car> clazz = Car.class;
    private final PrintStream printStream = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private static final String TEST_WORD = "test";
    private static final String PORSCHE_NAME = "포르쉐";
    private static final int PORSCHE_PRICE = 1_000_000;

    @DisplayName("Car 클래스의 모든 필드에 대한 정보를 출력한다.")
    @Test
    void showFieldsInfoTest() {
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> assertAll(
                        () -> assertThat(field.getName()).containsAnyOf("name", "price"),
                        () -> assertThat(field.getModifiers()).isEqualTo(2)
                ));
    }

    @DisplayName("Car 클래스의 모든 생성자에 대한 정보를 출력한다.")
    @Test
    void showConstructorsInfoTest() {
        Arrays.stream(clazz.getConstructors())
                .forEach(constructor -> {
                    assertAll(
                            () -> assertThat(constructor.getName()).isEqualTo("domain.step1.Car"),
                            () -> assertThat(constructor.getModifiers()).isOne()
                    );

                    Arrays.stream(constructor.getParameterTypes())
                            .forEach(parameter ->
                                    assertThat(parameter.getName()).containsAnyOf("java.lang.String", "int")
                            );
                });
    }

    @DisplayName("Car 클래스의 모든 메소드에 대한 정보를 출력한다.")
    @Test
    void showMethodsInfoTest() {
        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(method -> assertAll(
                        () -> assertThat(method.getName())
                                .containsAnyOf("printView", "testGetName", "testGetPrice", "getName", "getPrice"),
                        () -> assertThat(method.getModifiers()).isOne()
                ));
    }

    @DisplayName("Car 클래스에서 test 로 시작하는 메소드만 Java Reflection 을 활용해 실행한다.")
    @Test
    void testMethodRunTest() {
        Car porsche = new Car(PORSCHE_NAME, PORSCHE_PRICE);
        Method[] methods = clazz.getDeclaredMethods();

        List<Object> result = Arrays.stream(methods)
                .filter(method -> method.getName().startsWith(TEST_WORD))
                .map(method -> {
                    try {
                        return method.invoke(porsche);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(result).containsExactlyInAnyOrder("test : 포르쉐", "test : 1000000")
        );
    }

    @DisplayName("Car 클래스에서 @PrintView 애노테이션으로 설정되어 있는 메소드만 Java Reflection 을 활용해 실행한다.")
    @Test
    void testAnnotationMethodRunTest() {
        System.setOut(new PrintStream(outputStream));

        Car porsche = new Car(PORSCHE_NAME, PORSCHE_PRICE);
        Method[] methods = clazz.getDeclaredMethods();

        List<Object> result = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .map(method -> {
                    try {
                        return method.invoke(porsche);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(outputStream.toString().trim()).isEqualTo("자동차 정보를 출력 합니다."),
                () -> assertThat(result).hasSize(1)
        );

        System.setOut(printStream);
    }

    @DisplayName("Reflection API 를 활용해 다음 Car 클래스의 name 과 price 필드에 값을 할당한다.")
    @Test
    void privateFieldAccessTest() throws Exception {
        Car car = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("name")) {
                field.set(car, PORSCHE_NAME);
            } else if (field.getName().equals("price")) {
                field.set(car, PORSCHE_PRICE);
            }
        }

        assertThat(car.getName()).isEqualTo(PORSCHE_NAME);
        assertThat(car.getPrice()).isEqualTo(PORSCHE_PRICE);
    }

    @DisplayName("자바 Reflection API 를 활용해 Car 인스턴스를 생성한다. - 기본 생성자 이용")
    @Test
    void defaultConstructorTest() throws Exception {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                Car car = (Car) constructor.newInstance();
                assertAll(
                        () -> assertThat(car).isInstanceOf(Car.class),
                        () -> assertThat(car.getName()).isNull(),
                        () -> assertThat(car.getPrice()).isZero()
                );
            }
        }
    }

    @DisplayName("자바 Reflection API 를 활용해 Car 인스턴스를 생성한다. - 매개변수가 존재하는 생성자 이용")
    @Test
    void constructorWithArgsTest() throws Exception {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 2) {
                Car car = (Car) constructor.newInstance(PORSCHE_NAME, PORSCHE_PRICE);
                assertAll(
                        () -> assertThat(car).isInstanceOf(Car.class),
                        () -> assertThat(car.getName()).isEqualTo(PORSCHE_NAME),
                        () -> assertThat(car.getPrice()).isEqualTo(PORSCHE_PRICE)
                );
            }
        }
    }
}
