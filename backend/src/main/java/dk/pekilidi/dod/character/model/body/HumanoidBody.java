package dk.pekilidi.dod.character.model.body;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("HUMANOID")
@SuperBuilder(toBuilder = true)
public class HumanoidBody extends BaseBody implements Serializable {

  @Serial
  private static final long serialVersionUID = -7287994998222456079L;

  public static final int TOTAL_BODY_PARTS = 8;

  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "head_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "head_max_hp"))
  private BodyPart head;
  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "chest_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "chest_max_hp"))
  private BodyPart chest;
  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "stomach_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "stomach_max_hp"))
  private BodyPart stomach;
  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "right_arm_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "right_arm_max_hp"))
  private BodyPart rightArm;
  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "left_arm_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "left_arm_max_hp"))
  private BodyPart leftArm;
  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "right_leg_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "right_leg_max_hp"))
  private BodyPart rightLeg;
  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "left_leg_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "left_leg_max_hp"))
  private BodyPart leftLeg;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HumanoidBody that = (HumanoidBody) o;
    return getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
