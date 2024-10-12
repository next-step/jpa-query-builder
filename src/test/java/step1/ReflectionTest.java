package step1;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
- Car 객체 정보 가져오기
- Car 메소드 정보 가져오기
- Car Test메소드 실행하기
- @PrintView 애노테이션을 가진 메소드 가져오기
- @PrintView 애노테이션 메소드 실행
- private field에 값 할당
- 인자를 가진 생성자의 인스턴스 생성
*/
public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
        assertThat(carClass.getName()).isEqualTo("step1.Car");
    }

    @Test
    @DisplayName("Car 메소드 정보 가져오기")
    void showMethod() {
        Class<Car> carClass = Car.class;

        // Method 배열을 String 배열로 변환
        String[] methods = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::getName) // Method 객체를 문자열로 변환
                .toArray(String[]::new);

        assertThat(methods).containsExactly(
                "getName",
                "printView",
                "testGetName",
                "testGetPrice",
                "getPrice"
        );
    }

    @Test
    @DisplayName("Car Test메소드 실행하기")
    void executeTestMethod() throws Exception {
        Class<Car> carClass = Car.class;

        List<String> result = new ArrayList<>();

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                result.add(method.invoke(carClass.getDeclaredConstructor().newInstance()).toString());
            }
        }

        assertThat(result).containsExactly(
                "test : null",
                "test : 0"
        );
    }

    @Test
    @DisplayName("@PrintView 애노테이션을 가진 메소드 가져오기")
    void testAnnotationMethodGet() {
        Class<Car> carClass = Car.class;

        List<String> result = new ArrayList<>();

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrintView.class)) {
                result.add(method.getName());
            }
        }

        assertThat(result).containsExactly("printView");
    }

    @Test
    @DisplayName("@PrintView 애노테이션을 가진 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> carClass = Car.class;

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(carClass.getDeclaredConstructor().newInstance());
            }
        }
    }

    @Test
    @DisplayName("private 변수에 데이터 할당")
    void setPrivateFieldTest() throws Exception {
        Class<Car> carClass = Car.class;

        Car car = carClass.getDeclaredConstructor().newInstance();

        Field nameField = carClass.getDeclaredField("name");
        Field priceField = carClass.getDeclaredField("price");

        nameField.setAccessible(true);
        priceField.setAccessible(true);

        nameField.set(car, "SangkiHan");
        priceField.set(car, 1000);

        assertThat(car.getName()).isEqualTo("SangkiHan");
        assertThat(car.getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void requiredArgumentConstructorTest() throws Exception {
        Class<Car> carClass = Car.class;

        Constructor<Car> constructor = carClass.getDeclaredConstructor(String.class, int.class);

        Car car = constructor.newInstance("SangkiHan", 1000);

        assertThat(car.getName()).isEqualTo("SangkiHan");
        assertThat(car.getPrice()).isEqualTo(1000);
    }
}
