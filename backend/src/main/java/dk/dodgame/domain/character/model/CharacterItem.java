package dk.dodgame.domain.character.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.*;
import lombok.Builder.Default;

import dk.dodgame.domain.item.ItemKey;

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
  private double currentWeight;
  private double currentPrice;

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
