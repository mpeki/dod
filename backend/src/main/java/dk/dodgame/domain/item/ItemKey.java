package dk.dodgame.domain.item;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonValue;
import dk.dodgame.domain.action.model.ActionKey;
import dk.dodgame.domain.changerequest.model.ChangeKey;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ItemKey implements ChangeKey, ActionKey, Serializable {

  @Serial
  private static final long serialVersionUID = 6709033522862958772L;

  @JsonValue
  @JsonAlias("itemKey")
  @Column(name = "item_key")
  private String value;
  // https://hibernate.atlassian.net/browse/HHH-15307

  public static ItemKey toItemKey(String key) {
    return ItemKey.builder().value(key).build();
  }

  public ItemKey getValue() {
    return this;
  }

  public String getKeyValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ItemKey itemKey)) {
      return false;
    }
    return Objects.equals(value, itemKey.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
