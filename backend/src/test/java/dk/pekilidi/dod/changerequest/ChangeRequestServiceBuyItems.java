package dk.pekilidi.dod.changerequest;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.items.ItemService;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
public class ChangeRequestServiceBuyItems {

  @Autowired
  private CharacterService charService;
  @Autowired
  private ChangeRequestService changeRequestService;
  @Autowired
  private ItemService itemService;

}
