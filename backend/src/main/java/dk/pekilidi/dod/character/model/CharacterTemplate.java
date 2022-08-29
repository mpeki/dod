package dk.pekilidi.dod.character.model;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Builder(toBuilder = true)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CharacterTemplate implements Serializable {

    private static final long serialVersionUID = 4121021561881935955L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique=true)
    private String name;

    @OneToMany
    private List<BaseTraitRule> baseTraitRules;

    private Class bodyTypeClass;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CharacterTemplate that = (CharacterTemplate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
