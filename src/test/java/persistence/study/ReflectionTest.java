package persistence.study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private Class<Car> carClass;

    final String NAME = "메르세데스 벤츠";
    final int PRICE = 1000;

    @BeforeEach
    void setUp() {
        carClass = Car.class;
    }

    @Test
    @DisplayName("요구사항1. Car 클래스의 모든 필드, 생성자, 메소드 정보를 출력한다.")
    void showClass() {
        List<String> fields = convertArrayToListWithMapping(carClass.getDeclaredFields(), Field::getName);
        List<String> constructor = convertArrayToListWithMapping(carClass.getDeclaredConstructors(), Constructor::getName);
        List<String> methods = convertArrayToListWithMapping(carClass.getDeclaredMethods(), Method::getName);

        assertThat(fields).containsOnly("name", "price");
        assertThat(constructor).containsOnly("persistence.study.Car", "persistence.study.Car");
        assertThat(methods).containsOnly("testGetPrice", "printView", "testGetName", "getName", "getPrice");
    }

    private <T, R> List<R> convertArrayToListWithMapping(T[] array, Function<T, R> map) {
        return Arrays.stream(array).map(map).collect(Collectors.toList());
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws InvocationTargetException, IllegalAccessException {
        carClass = Car.class;
        Car car = new Car();
        Method[] methods = carClass.getMethods();
        for (Method method : methods) {
            isStartWithTest(car, method);
        }
    }

    private void isStartWithTest(final Car car, final Method method) throws IllegalAccessException, InvocationTargetException {
        if (method.getName().startsWith("test")) {
            String invoke = (String) method.invoke(car);
            assertThat(invoke).startsWith("test : ");
        }
    }


    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws InvocationTargetException, IllegalAccessException {
        Car car = new Car();
        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        List<Method> methodWithPrintView = Arrays.stream(carClass.getMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class)).collect(Collectors.toList());
        for (Method method : methodWithPrintView) {
            method.invoke(car);
            assertThat(out.toString().trim()).isEqualTo("자동차 정보를 출력 합니다.");
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Car car = carClass.getDeclaredConstructor().newInstance();

        Field nameFiled = getAccessibleFiled(carClass, "name");
        Field priceFiled = getAccessibleFiled(carClass, "price");
        nameFiled.set(car, NAME);
        priceFiled.set(car, PRICE);

        assertThat(car.getName()).isEqualTo(NAME);
        assertThat(car.getPrice()).isEqualTo(PRICE);
    }

    private Field getAccessibleFiled(final Class<?> clazz, final String filedName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(filedName);
        field.setAccessible(true);
        return field;
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<?> constructorWithArgs = Arrays.stream(carClass.getDeclaredConstructors())
                .filter(constructor -> hasParameters(constructor.getParameters()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("인자를 가진 생성자가 존재하지 않습니다."));

        Car car = (Car) constructorWithArgs.newInstance(NAME, PRICE);
        assertThat(car.getName()).isEqualTo(NAME);
        assertThat(car.getPrice()).isEqualTo(PRICE);
    }

    private boolean hasParameters(final Parameter[] parameters) {
        return parameters.length > 0;
    }
}