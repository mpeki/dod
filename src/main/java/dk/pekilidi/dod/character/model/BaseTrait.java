package dk.pekilidi.dod.character.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BaseTrait implements Serializable {

    private static final long serialVersionUID = 2277570107538234893L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DODCharacter character;

    @NonNull
    private String traitName;
    private int value;
    private int startValue;
    private int groupValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTrait baseTrait = (BaseTrait) o;
        return value == baseTrait.value && startValue == baseTrait.startValue && groupValue == baseTrait.groupValue && id.equals(baseTrait.id) && traitName.equals(baseTrait.traitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, traitName, value, startValue, groupValue);
    }

    @Override
    public String toString() {
        return "BaseTrait{" +
                "id=" + id +
                ", traitName='" + traitName + '\'' +
                ", value=" + value +
                ", startValue=" + startValue +
                ", groupValue=" + groupValue +
                '}';
    }
}