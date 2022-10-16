package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.skill.model.Skill;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "skill_id", referencedColumnName = "id")
  private Skill skillId;
  /*
    @Embedded
    @AttributeOverride( name = "value", column = @Column(name = "skill_key"))
    private SkillKey key;
  */
  private int fv;
  private int experience;
}
