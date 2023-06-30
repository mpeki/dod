package dk.pekilidi.dod.items.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.pekilidi.dod.items.ItemKey;
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
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private String id;
  @Embedded
  @AttributeOverride(name = "key", column = @Column(name = "item_key"))
  private ItemKey key;
  @Column(name = "item_type", insertable = false, updatable = false)
  private ItemType itemType;
  private Double weight;
  private Integer price;
  private boolean breakable;
}
