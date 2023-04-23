package dk.pekilidi.dod.character.model.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "body_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseBody implements Serializable {

  private static final long serialVersionUID = -4047526321333706702L;

  @Id @Tsid
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private String id;

  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "total_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "total_max_hp"))
  private BodyPart total;
}
