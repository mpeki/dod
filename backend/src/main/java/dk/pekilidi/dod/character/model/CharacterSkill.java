package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.skill.SkillKey;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CharacterSkill implements Serializable {

  @Serial
  private static final long serialVersionUID = 3712820377469917115L;
  @Column(name = "skill_key")
  @Embedded
  public SkillKey skillKey;
  @Embedded
  private ItemKey itemKey;
  @Id
  @Tsid
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;
  private int fv;
  private int experience;
  private int skillPointsSpent;
  private LocalDateTime lastUsed;


  @Transient
  public String getValue() {
    return skillKey.getKeyValue();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CharacterSkill that)) {
      return false;
    }
    return fv == that.fv && experience == that.experience && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fv, experience);
  }
}
