package dk.dodgame.domain.character.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.*;

import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.skill.SkillKey;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CharacterSkill implements Serializable {

	@Serial
	private static final long serialVersionUID = 3712820377469917115L;

	@Column(name = "skill_key")
	@Embedded
	public SkillKey skillKey;

	@Embedded
	private ItemKey itemKey;

	@Id
	@Tsid
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private int fv;
	private int experience;
	private int skillPointsSpent;
	private LocalDateTime lastUsed;


	@Transient
	public String getValue() {
		return skillKey.getKeyValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CharacterSkill that)) {
			return false;
		}
		return fv == that.fv && experience == that.experience && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fv, experience);
	}
}
