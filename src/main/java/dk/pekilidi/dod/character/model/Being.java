package dk.pekilidi.dod.character.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Being {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  private String name;

  @NonNull
  @OneToOne
  private Race race;

}
