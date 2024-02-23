package persistence.study;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	@DisplayName("Car 객체 정보 가져오기")
	void showCarClass() {
		Class<Car> carClass = Car.class;
		logger.debug(carClass.getName());

		Field[] fields = carClass.getDeclaredFields();
		logger.debug("필드:");
		for (Field field : fields) {
			logger.debug(String.valueOf(field));
		}

		Constructor<?>[] constructors = carClass.getDeclaredConstructors();
		logger.debug("생성자:");
		for (Constructor<?> constructor : constructors) {
			logger.debug(String.valueOf(constructor));
		}

		Method[] methods = carClass.getDeclaredMethods();
		logger.debug("메서드:");
		for (Method method : methods) {
			logger.debug(String.valueOf(method));
		}
	}

	@Test
	void test_로_시작하는_메소드_실행() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Class<Car> carClass = Car.class;
		Car car = carClass.getConstructor().newInstance();
		Arrays.stream(carClass.getDeclaredMethods())
			.filter(x -> x.getName().startsWith("test"))
			.forEach(x -> {
				try {
					x.invoke(car);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
	}

	@Test
	void PrintView_어노테이션_메소드_실행() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Class<Car> carClass = Car.class;
		Car car = carClass.getConstructor().newInstance();
		Arrays.stream(carClass.getDeclaredMethods())
			.filter( x -> x.isAnnotationPresent(PrintView.class))
			.forEach(x -> {
				try {
					x.invoke(car);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
	}

	@Test
	public void privateField_값_할당() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		Class<Car> clazz = Car.class;
		logger.debug(clazz.getName());

		String name = "소나타";
		int price = 1000;

		Car car = clazz.getConstructor().newInstance();
		Field nameField = clazz.getDeclaredField("name");
		nameField.setAccessible(true);
		nameField.set(car, name);

		Field priceField = clazz.getDeclaredField("price");
		priceField.setAccessible(true);
		priceField.set(car, price);

		assertAll(
			() -> {
				assertEquals(car.testGetName(), "test : " + name);
				assertEquals(car.testGetPrice(), "test : " + price);
			}
		);
	}

	@Test
	void 인자를_가진_생성자의_인스턴스_생성() throws InvocationTargetException, InstantiationException, IllegalAccessException {
		Class<Car> clazz = Car.class;
		String name = "모닝";
		int price = 2000;

		Constructor<?> constructor = Arrays.stream(clazz.getDeclaredConstructors())
			.filter(x -> x.getParameterCount() > 0)
			.findFirst().orElseThrow();
		Car car = (Car) constructor.newInstance(name, price);

		assertAll(
			() -> {
				assertEquals(car.testGetName(), "test : " + name);
				assertEquals(car.testGetPrice(), "test : " + price);
			}
		);
	}
}
