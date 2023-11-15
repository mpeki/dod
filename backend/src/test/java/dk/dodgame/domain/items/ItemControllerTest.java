package dk.dodgame.domain.items;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dodgame.BaseControllerTest;
import dk.dodgame.data.ItemDTO;
import dk.dodgame.domain.item.ItemNotFoundException;
import dk.dodgame.domain.item.ItemService;
import dk.dodgame.domain.item.model.ItemType;
import dk.dodgame.domain.item.model.MeleeWeapon;
import dk.dodgame.util.RandomObjectFiller;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@Tag("regression")
class ItemControllerTest extends BaseControllerTest {

  RandomObjectFiller objFill = new RandomObjectFiller();
  ItemDTO item1;
  ItemDTO item2;
  ItemDTO item3;

  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @MockBean
  private ItemService itemService;

  @BeforeEach
  void setUp() {
    item1 = objFill.createAndFill(ItemDTO.class);
    item2 = objFill.createAndFill(ItemDTO.class);
    item3 = objFill.createAndFill(ItemDTO.class);
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void findItemByKey() throws Exception {
    given(itemService.findItemByKey(item1.getItemKey().getKeyValue())).willReturn(item1);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/items/key/" + item1.getItemKey())
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(item1)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  void findAllItems() throws Exception {
    List<ItemDTO> items = List.of(item1, item2, item3);
    given(itemService.findAll()).willReturn(items);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(items)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void findByType() throws Exception {
    List<ItemDTO> items = List.of(item1, item2);
    given(itemService.findItemByType(ItemType.MELEE_WEAPON)).willReturn(items);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/items/type/MELEE_WEAPON")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(items)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "system", password = "system", roles = {"system"})
  void createMeleeWeapon() throws Exception {
    given(itemService.createItem(item1, MeleeWeapon.class)).willReturn(item2);
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/items/melee_weapon")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(item1)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void getItemNotFound() throws Exception {
    given(itemService.findItemByKey(anyString())).willThrow(new ItemNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/item/key/no-a-skill")).andExpect(status().isNotFound());
  }
}