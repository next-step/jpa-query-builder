package persistence.study;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReflectionTest {
    Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("요구사항 1 - 클래스 정보 출력")
    void showClass() {
        Class<Car> clazz = Car.class;

        Field[] declaredFields = clazz.getDeclaredFields();
        Constructor<?>[] constructors = clazz.getConstructors();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        Arrays.stream(declaredFields).forEach(field -> log.debug("field: {}", field));
        Arrays.stream(constructors).forEach(constructor -> log.debug("constructor: {}", constructor));
        Arrays.stream(declaredMethods).forEach(declaredMethod -> log.debug("method: {}", declaredMethod));
    }

    @Test
    @DisplayName("요구사항 2 - test 로 시작하는 메소드 실행")
    void testMethodRun() {
        Class<Car> clazz = Car.class;
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    Object result = invokeTargetMethod(method, clazz);

                    assertThat(method.getName()).startsWith("test");
                    assertThat(result).isInstanceOf(String.class);
                    assertThat(String.valueOf(result)).startsWith("test : ");
                });
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        Class<Car> clazz = Car.class;
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    Object result = invokeTargetMethod(method, clazz);

                    assertThat(method.isAnnotationPresent(PrintView.class)).isTrue();
                    assertThat(result).isNull();
                    assertThat(outputStreamCaptor.toString().trim()).isEqualTo("자동차 정보를 출력 합니다.");
                });
    }

    @Test
    @DisplayName("요구사항 4 - private field 에 값 할당")
    void privateFieldAccess() {
        Class<Car> clazz = Car.class;
        Car car = new Car();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> Modifier.isPrivate(field.getModifiers()))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        if (field.getName().equals("name")) {
                            field.set(car, "테스트");
                        }
                        if (field.getName().equals("price")) {
                            field.set(car, 1000);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        assertThat(car).isEqualTo(new Car("테스트", 1000));
    }

    @Test
    @DisplayName("요구사항 5 - 인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> clazz = Car.class;
        Constructor<?> findConstructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() > 0)
                .findAny()
                .orElseThrow(() -> new RuntimeException("인자를 가진 생성자가 없습니다."));

        assertThat(findConstructor.newInstance("테스트", 1000)).isEqualTo(new Car("테스트", 1000));
    }

    private static Object invokeTargetMethod(Method method, Class<Car> clazz) {
        try {
            return method.invoke(clazz.getConstructor().newInstance());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
