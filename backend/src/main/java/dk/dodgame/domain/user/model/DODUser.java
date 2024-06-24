package dk.dodgame.domain.user.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DODUser implements Serializable {

  @Serial
  private static final long serialVersionUID = 3038461999185846610L;

  @Id
  @Tsid
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;
  private String username;
  @Builder.Default
  @Column(columnDefinition = "int default 10")
  private Integer maxNpcs = 10;}
