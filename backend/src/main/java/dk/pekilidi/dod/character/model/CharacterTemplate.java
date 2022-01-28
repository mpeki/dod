package dk.pekilidi.dod.character.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
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

}
