package dk.pekilidi.dod.character.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Race {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  private String name;
}
