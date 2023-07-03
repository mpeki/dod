package dk.pekilidi.dod.changerequest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.transaction.NotSupportedException;

@JsonDeserialize(using = ChangeKeyDeserializer.class)
public interface ChangeKey {
  static ChangeKey valueOf(String value) throws NotSupportedException {
    throw new NotSupportedException("Not supported");
  }
}
