package dk.pekilidi.dod.character.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BaseTraitRule implements Serializable {

    private static final long serialVersionUID = -3987106087156703611L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baseTraitName;
    private String baseTraitDieRoll;
    private String baseTraitHeroDieRoll;
}
