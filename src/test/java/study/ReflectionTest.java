package study;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {

  private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

  @Test
  @DisplayName("Car 객체 정보 가져오기")
  void showClass() {
    Class<Car> carClass = Car.class;
    logger.debug("클래스 이름");
    logger.debug(carClass.getName());

    logger.debug("객체 필드 목록");
    Field[] declaredFileds = carClass.getDeclaredFields();
    for (Field field : declaredFileds) {
      logger.debug(String.valueOf(field));
    }

    Constructor[] constructors = carClass.getConstructors();
    logger.debug("객체 생성자 목록");
    for (Constructor constructor : constructors) {
      logger.debug(constructor.getName());
      logger.debug(Arrays.toString(constructor.getParameterTypes()));
    }

    Method[] methods = carClass.getDeclaredMethods();
    logger.debug("객체 메서드 목록");
    for (Method method : methods) {
      logger.debug(method.getName());
      logger.debug(Arrays.toString(method.getParameterTypes()));
    }
  }
}
