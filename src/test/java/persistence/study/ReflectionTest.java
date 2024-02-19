package persistence.study;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReflectionTest {

    private static final String TEST = "test";
    private static final String NAME_KEY = "name";
    private static final String PRICE_KEY = "price";
    private static final String NAME_VALUE = "자동차";
    private static final int PRICE_VALUE = 1;
    Class<Car> carClass = Car.class;

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void Car_객체_정보_가져오기() {

        assertAll(
            () -> assertThat(carClass.getDeclaredFields()).hasSize(2),
            () -> assertThat(carClass.getDeclaredMethods()).hasSize(5),
            () -> assertThat(carClass.getDeclaredConstructors()).hasSize(2)
        );
    }

    @Test
    @DisplayName("Car 객체의 test로 시작하는 메소드 실행")
    void test로_시작하는_메소드_실행() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Car car = carClass.getDeclaredConstructor(String.class, int.class).newInstance(NAME_VALUE, PRICE_VALUE);

        assertAll(() -> assertThat(Arrays.stream(carClass.getMethods())
                .filter(method -> method.getName().startsWith(TEST))
                .map(method -> {
                    try {
                        return (String) method.invoke(car);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
            ).containsAnyOf(TEST + " : " + NAME_VALUE, TEST + " : " + PRICE_VALUE)
        );
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void PrintView_애노테이션_메소드_실행() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Car car = carClass.getDeclaredConstructor().newInstance();

        Arrays.stream(carClass.getMethods())
            .filter(method -> method.isAnnotationPresent(PrintView.class))
            .forEach(method -> {
                try {
                    method.invoke(car);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });

        System.setOut(originalOut);
        assertThat(outContent.toString().trim()).isEqualTo("자동차 정보를 출력 합니다.");
    }

    @Test
    @DisplayName("private field에 값 할당")
    public void private_타입_필드에_값_할당() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Car car = carClass.getDeclaredConstructor().newInstance();

        Map<String, Object> map = Map.of(NAME_KEY, NAME_VALUE, PRICE_KEY, PRICE_VALUE);

        map.forEach((key, value) -> {
            try {
                Field field = carClass.getDeclaredField(key);
                field.setAccessible(true);
                field.set(car, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        assertAll(
            () -> assertThat(car.getName()).isEqualTo(map.get(NAME_KEY)),
            () -> assertThat(car.getPrice()).isEqualTo(map.get(PRICE_KEY))
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void 인자를_가진_인스턴스_생성() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Car car = carClass.getDeclaredConstructor(String.class, int.class).newInstance(NAME_VALUE, PRICE_VALUE);

        assertAll(
            () -> assertThat(car.getName()).isEqualTo(NAME_VALUE),
            () -> assertThat(car.getPrice()).isEqualTo(PRICE_VALUE)
        );
    }
}

