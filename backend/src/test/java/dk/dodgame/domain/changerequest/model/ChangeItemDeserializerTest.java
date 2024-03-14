package dk.dodgame.domain.changerequest.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dodgame.data.PaymentDTO;
import dk.dodgame.domain.skill.SkillKey;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("regression")
class ChangeItemDeserializerTest {

    static final String changeRequestJSON = """
{
  "changeType": "NEW_ITEM_INIT_COMPLETE",
  "changeDescription": "Buy new item",
  "changeKey": "leather.helm",
  "secondaryChangeKey": {
    "changeType": "NEW_ITEM_PAYMENT",
    "changeKey": "",
    "changeItem": {
      "gold": 0,
      "silver": 15,
      "copper": 0
    }
  },
  "modifier": 1,
  "status": "PENDING",
  "statusLabel": "CHANGE_REQUEST_PENDING"
}
    """;

    static final String changeRequestJSON_empty = """
{
  "changeType": "NEW_ITEM_INIT_COMPLETE",
  "changeDescription": "Buy new item",
  "changeKey": "leather.helm",
  "secondaryChangeKey": {
    "changeType": "NEW_ITEM_PAYMENT",
    "changeKey": "",
    "changeItem": {
    }
  },
  "modifier": 1,
  "status": "PENDING",
  "statusLabel": "CHANGE_REQUEST_PENDING"
}
    """;

    @Test
    void test() {
        ChangeItemDeserializer deserializer = new ChangeItemDeserializer();
        assertThrows(NullPointerException.class, () -> deserializer.deserialize(null, null));
    }

    @Test
    void testDeserializeSkillChangeRequest() throws JsonProcessingException {
        ChangeRequest changeRequest = new ObjectMapper()
                .readerFor(ChangeRequest.class)
                .readValue(changeRequestJSON);
        assertNotNull(changeRequest.getChangeKey());
        assertEquals(PaymentDTO.class, changeRequest.getSecondaryChangeKey().getChangeItem().getClass());
        assertEquals(15, ((PaymentDTO)changeRequest.getSecondaryChangeKey().getChangeItem()).getSilver());
    }

    @Test
    void testDeserializeSkillChangeRequest_emptyChangeItem() throws JsonProcessingException {
        ChangeRequest changeRequest = new ObjectMapper()
                .readerFor(ChangeRequest.class)
                .readValue(changeRequestJSON_empty);
        assertNotNull(changeRequest.getChangeKey());
        assertEquals(PaymentDTO.class, changeRequest.getSecondaryChangeKey().getChangeItem().getClass());
        assertEquals(0, ((PaymentDTO)changeRequest.getSecondaryChangeKey().getChangeItem()).getSilver());
    }
}
