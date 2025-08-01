package dk.dodgame.domain.character.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

import org.hibernate.Hibernate;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.*;

import dk.dodgame.domain.character.model.body.BaseBody;

@Builder(toBuilder = true)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class CharacterTemplate implements Serializable {

  @Serial
  private static final long serialVersionUID = 4121021561881935955L;

  @Id @Tsid
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @NonNull
  @Column(unique = true)
  private String name;

  @OneToMany
  private List<BaseTraitRule> baseTraitRules;

  private Class<BaseBody> bodyTypeClass;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CharacterTemplate that) || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
