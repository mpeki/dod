package dk.dodgame.domain.item.model;

import jakarta.persistence.Entity;
import java.io.Serial;
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
@SuperBuilder(toBuilder = true)
public class Weapon extends BaseItem {

  @Serial
  private static final long serialVersionUID = -6233955812034046892L;

  private String damage;
  private HandGrip handGrip;
  private Integer length;
  private Integer bp;
  private Integer strengthGroup;

}
