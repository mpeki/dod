package dk.dodgame.domain.user.model;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import io.hypersistence.utils.hibernate.id.Tsid;

import lombok.*;

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
  private String id;
  private String username;
  @Builder.Default
  @Column(columnDefinition = "int default 10")
  private Integer maxNpcs = 10;}
