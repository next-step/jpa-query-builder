package persistence.study;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
  private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
  private final Class<Car> carClass = Car.class;

  @Test
  @DisplayName("Car 객체 정보 가져오기")
  void showClass() {
    String carClassName = carClass.getName();
    logger.debug(carClassName);
  }
}