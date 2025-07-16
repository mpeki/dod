package dk.dodgame.domain.character.model.body;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "body")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "body_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseBody implements Serializable {

  @Serial
  private static final long serialVersionUID = -4047526321333706702L;

  @Id @Tsid
  @JsonIgnore
  private String id;

  @Embedded
  @AttributeOverride(name = "currentHP", column = @Column(name = "total_hp"))
  @AttributeOverride(name = "maxHP", column = @Column(name = "total_max_hp"))
  private BodyPart total;
}
