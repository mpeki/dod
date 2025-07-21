package dk.dodgame.domain.item.model;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import dk.dodgame.domain.item.ItemKey;

@Getter
@Setter
@Entity
@Table(name = "item")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class BaseItem implements Serializable {

  @Serial
  private static final long serialVersionUID = -6434166882765426935L;

  @Id
  @Tsid
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;
  @Embedded
  @AttributeOverride(name = "key", column = @Column(name = "item_key"))
  private ItemKey key;
  @Column(name = "item_type", insertable = false, updatable = false)
  private ItemType itemType;
  private Double weight;
  private Double price;
  private boolean breakable;
}
