package dk.pekilidi.dod.character.model;

import jakarta.persistence.Column;
import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Looks implements Serializable {

  @Serial
  private static final long serialVersionUID = 5626332190934239576L;
  EyeColor eyeColor;
  Voice voice;
  HairColor hairColor;
  HairLength hairLength;
  BeardLength beardLength;
  @Default
  @Column(columnDefinition = "int default -1")
  int height = -1;
  @Default
  @Column(columnDefinition = "int default -1")
  int weight = -1;

  public enum EyeColor {BLUE, CHESSNUT_BROWN, BROWN, BLUE_GRAY, STEEL_GRAY, SILVER, GOLDEN, GREEN}

  public enum Voice {SOFT, LOUD, SHARP, NORMAL, HOARSE, BOOMING, HISSING}

  public enum HairColor {BLACK, BROWN, BLOND, RED, WHITE, STEELGRAY}

  public enum HairLength {BALD, HALF_BALD, SHORT, NECK_LONG, SHOULDER_LONG, BACK_LONG}

  public enum BeardLength {NONE, MOUSTACHE, BEARD, BEARD_AND_MOUSTACHE}
}
