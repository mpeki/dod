package dk.pekilidi.dod.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtAuthConverterProperties {

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getPrincipalAttribute() {
    return principalAttribute;
  }

  public void setPrincipalAttribute(String principalAttribute) {
    this.principalAttribute = principalAttribute;
  }

  @NotBlank
  private String resourceId;
  private String principalAttribute;
}