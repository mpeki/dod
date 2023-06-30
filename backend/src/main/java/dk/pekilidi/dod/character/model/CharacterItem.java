package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.items.ItemKey;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CharacterItem {

  @Id
  @Tsid
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Column(name = "item_key")
  @Embedded
  private ItemKey itemKey;
  private ItemLocation location;
  private String itemName;
  private LocalDateTime timeAcquired;
  @Default
  private LocalDateTime timeCreated = LocalDateTime.now();
  @Default
  private int currentHealth = 100;
  @Default
  private int quantity = 1;

  @Transient
  public String getValue() {
    return itemKey.getKeyValue();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CharacterItem that)) {
      return false;
    }
    return currentHealth == that.currentHealth && timeCreated.equals(that.timeCreated) && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timeCreated, currentHealth);
  }
}
