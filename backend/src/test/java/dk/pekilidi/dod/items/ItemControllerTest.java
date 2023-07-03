package dk.pekilidi.dod.items;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.data.ItemDTO;
import dk.pekilidi.dod.items.model.ItemType;
import dk.pekilidi.dod.items.model.MeleeWeapon;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(ItemController.class)
@Tag("regression")
class ItemControllerTest {

  RandomObjectFiller objFill = new RandomObjectFiller();
  ItemDTO item1;
  ItemDTO item2;
  ItemDTO item3;

  @Autowired
  private ObjectMapper jacksonObjectMapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ItemService itemService;

  @BeforeEach
  void setUp() {
    item1 = objFill.createAndFill(ItemDTO.class);
    item2 = objFill.createAndFill(ItemDTO.class);
    item3 = objFill.createAndFill(ItemDTO.class);
  }

  @Test
  void findItemByKey() throws Exception {
    given(itemService.findItemByKey(item1.getItemKey().getKeyValue())).willReturn(item1);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/item/key/" + item1.getItemKey())
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
            .get("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(items)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  void findByType() throws Exception {
    List<ItemDTO> items = List.of(item1, item2);
    given(itemService.findItemByType(ItemType.MELEE_WEAPON)).willReturn(items);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/items/type/MELEE_WEAPON")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(items)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  void createMeleeWeapon() throws Exception {
    given(itemService.createItem(item1, MeleeWeapon.class)).willReturn(item2);
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/item/melee_weapon")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(item1)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  void getItemNotFound() throws Exception {
    given(itemService.findItemByKey(anyString())).willThrow(new ItemNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/skill/key/no-a-skill")).andExpect(status().isNotFound());
  }
}