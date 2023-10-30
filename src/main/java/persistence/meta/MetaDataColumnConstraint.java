package persistence.meta;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class MetaDataColumnConstraint {
  private final ConstraintType constraintType;
  private final Annotation annotation;

  private MetaDataColumnConstraint(ConstraintType constraintType, Annotation annotation) {
    this.constraintType = constraintType;
    this.annotation = annotation;
  }

  public static MetaDataColumnConstraint of(Annotation annotation){
    ConstraintType constraintType = Arrays.stream(ConstraintType.values())
            .filter(constraintGroupType -> constraintGroupType.hasConstraint(annotation))
            .findFirst()
            .orElseGet(() -> ConstraintType.EMPTY);

    return new MetaDataColumnConstraint(constraintType, annotation);
  }

  public String getConstraint(){
    return this.constraintType.getConstraintMappingToConstraint(this.annotation);
  }

  public boolean isPrimaryKey(){
    return this.constraintType.equals(ConstraintType.ID);
  }
}
