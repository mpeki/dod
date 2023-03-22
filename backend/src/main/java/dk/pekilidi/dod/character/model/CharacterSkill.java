package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.skill.model.Skill;
import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CharacterSkill implements Serializable {

  private static final long serialVersionUID = 3712820377469917115L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "skill_id", referencedColumnName = "id")
  private Skill skillId;
  private int fv;
  private int experience;
}
