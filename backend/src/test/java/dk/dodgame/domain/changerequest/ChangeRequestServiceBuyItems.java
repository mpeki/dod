package dk.dodgame.domain.changerequest;

import dk.dodgame.DodApplication;
import dk.dodgame.domain.changerequest.ChangeRequestService;
import dk.dodgame.domain.character.CharacterService;
import dk.dodgame.domain.item.ItemService;
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
