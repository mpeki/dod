package dk.pekilidi.dod.character.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name", "character_template_id"})
)
public class Race implements Serializable {

  private static final long serialVersionUID = -8860486223584199305L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @Column(name="name", unique=true)
  private String name;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "character_template_id", referencedColumnName = "id")
  @JsonIgnore
  private CharacterTemplate characterTemplate;
}
