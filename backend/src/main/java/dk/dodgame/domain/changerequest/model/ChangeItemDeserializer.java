package dk.dodgame.domain.changerequest.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import dk.dodgame.data.PaymentDTO;
import java.io.IOException;

public class ChangeItemDeserializer extends JsonDeserializer<ChangeItem> {

  @Override
  public ChangeItem deserialize(JsonParser jp, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    JsonNode node = jp.getCodec().readTree(jp);
    // Assuming gold, silver, and copper are the only mandatory fields
    int gold = (node.get("gold") != null) ? node.get("gold").intValue() : 0;
    int silver = (node.get("silver") != null) ? node.get("silver").intValue() : 0;
    int copper = (node.get("copper") != null) ? node.get("copper").intValue() : 0;

    // Create and return the PaymentDTO instance
    return PaymentDTO.builder()
        .gold(gold)
        .silver(silver)
        .copper(copper)
        // Set other fields as necessary, potentially parsing more complex structures
        .build();
  }
}
