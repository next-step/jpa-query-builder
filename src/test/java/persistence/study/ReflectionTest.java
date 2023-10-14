package persistence.study;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	@DisplayName("Car 객체 정보 가져오기")
	void showClass() {
		Class<Car> carClass = Car.class;

		logger.debug(carClass.getName());
	}

	@Test
	@DisplayName("Car 메서드 네임이 test로 시작하는 메서드 실행하기")
	void executeMethodStartsWithTest() throws Exception {
		// given
		Class<Car> carClass = Car.class;
		Method[] declaredMethods = carClass.getDeclaredMethods();
		Constructor<Car> declaredConstructor = carClass.getDeclaredConstructor();
		Car car = declaredConstructor.newInstance();

		// when
		for (Method declaredMethod : declaredMethods) {
			if (declaredMethod.getName().startsWith("test")) {
				declaredMethod.invoke(car);
			}
		}
	}
}
