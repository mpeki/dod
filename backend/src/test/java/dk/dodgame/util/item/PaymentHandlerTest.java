package dk.dodgame.util.item;

import dk.dodgame.data.*;
import dk.dodgame.domain.changerequest.model.ChangeStatus;
import dk.dodgame.domain.changerequest.model.ChangeStatusLabel;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.model.Coin;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("regression")
class PaymentHandlerTest {

  private PaymentDTO basicPayment;
  private CharacterDTO initialPayingParty = CharacterDTO.builder().state(CharacterState.INIT_COMPLETE).build();

  @BeforeEach
  void setUp() {

    initialPayingParty.addBaseTrait(BaseTraitDTO.builder()
            .traitName(BaseTraitName.SIZE)
              .startValue(10)
              .currentValue(10).build());

    initialPayingParty.addCoins(10, Coin.GOLD_PIECE);
    initialPayingParty.addCoins(100, Coin.SILVER_PIECE);
    initialPayingParty.addCoins(500, Coin.COPPER_PIECE);

    CharacterItemDTO itemToBuy = CharacterItemDTO
        .builder()
        .item(ItemDTO.builder().itemKey(ItemKey.toItemKey("item.to.buy")).build())
        .build();

    basicPayment = PaymentDTO
        .builder()
        .payingParty(initialPayingParty)
        .itemsExchanged(List.of(itemToBuy))
        .gold(5)
        .silver(10)
        .copper(100)
        .build();
  }

  @Test
  void handleCharacterCreationPayment_success() {
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);
    PaymentHandler.handleCharacterCreationPayment(basicPayment);
    assertSame(ChangeStatus.APPROVED, basicPayment.getStatus());
    assertEquals(4, basicPayment.getPayingParty().getItems().keySet().size());
  }

  @Test
  void handleCharacter_setPayingParty() {
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);
    assertSame(initialPayingParty, basicPayment.getPayingParty());
    PaymentHandler.setPayingParty(basicPayment, CharacterDTO.builder().name("This one has a name").build());
    assertNotSame(initialPayingParty, basicPayment.getPayingParty());
  }

  @Test
  void handleCharacter_setPaymentItems() {
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);

    Exception exception = assertThrows(PaymentException.class, () -> {
      PaymentHandler.setPaymentItems(null, null, 0);
    });
    assertEquals("No payment received!", exception.getMessage());
    assertEquals(1, basicPayment.getItemsExchanged().size());
    PaymentHandler.setPaymentItems(basicPayment, ItemDTO.builder().itemKey(ItemKey.toItemKey("some.item")).build(), 3);
    assertEquals(3, basicPayment.getItemsExchanged().size());
    PaymentHandler.setPayingParty(basicPayment, null);
    PaymentHandler.setPaymentItems(basicPayment, ItemDTO.builder().itemKey(ItemKey.toItemKey("some.item")).build(), 1);
    assertEquals(3, basicPayment.getItemsExchanged().size()); //should still be 3 since paying party is null
    assertEquals(ChangeStatus.REJECTED, basicPayment.getStatus());
    assertEquals(ChangeStatusLabel.NO_PAYING_PARTY, basicPayment.getStatusLabel());

  }

  @Test
  void handleCharacter_setPaymentItems_armor() {
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);

    Exception exception = assertThrows(PaymentException.class, () -> {
      PaymentHandler.setPaymentItems(null, null, 0);
    });
    assertEquals("No payment received!", exception.getMessage());
    assertEquals(1, basicPayment.getItemsExchanged().size());
    PaymentHandler.setPaymentItems(basicPayment, ItemDTO.builder().itemKey(ItemKey.toItemKey("some.armor")).weightReference("A").weight(2.0).price(20.0).build(), 3);
    assertEquals(3, basicPayment.getItemsExchanged().size());
    PaymentHandler.setPayingParty(basicPayment, null);
    PaymentHandler.setPaymentItems(basicPayment, ItemDTO.builder().itemKey(ItemKey.toItemKey("some.armor")).build(), 1);
    assertEquals(3, basicPayment.getItemsExchanged().size()); //should still be 3 since paying party is null
    assertEquals(ChangeStatus.REJECTED, basicPayment.getStatus());
    assertEquals(ChangeStatusLabel.NO_PAYING_PARTY, basicPayment.getStatusLabel());

  }


  @Test
  void handleCharacterCreationPayment_InsufficientFunds() {
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(71.0);
    PaymentHandler.handleCharacterCreationPayment(basicPayment);
    assertSame(ChangeStatus.REJECTED, basicPayment.getStatus());
    assertSame(ChangeStatusLabel.INSUFFICIENT_FUNDS, basicPayment.getStatusLabel());
  }


  @Test
  void handleCharacterCreationPayment_NoPayment() {
    basicPayment = null;
    Exception exception = assertThrows(PaymentException.class, () -> {
      PaymentHandler.handleCharacterCreationPayment(basicPayment);
    });
    assertTrue(exception.getMessage().contains("Payment is null!"));
  }

  @Test
  void handleCharacterCreationPayment_NoPayingParty() {
    basicPayment.setPayingParty(null);
      PaymentHandler.handleCharacterCreationPayment(basicPayment);
    assertSame(ChangeStatus.REJECTED, basicPayment.getStatus());
    assertSame(ChangeStatusLabel.NO_PAYING_PARTY, basicPayment.getStatusLabel());
  }

  @Test
  void handleCharacterCreationPayment_noItemException() {
    basicPayment.setItemsExchanged(null);
    PaymentHandler.handleCharacterCreationPayment(basicPayment);
    assertSame(ChangeStatus.REJECTED, basicPayment.getStatus());
    assertSame(ChangeStatusLabel.NO_ITEMS_IN_PAYMENT, basicPayment.getStatusLabel());
  }

  @ParameterizedTest
  @EnumSource(CharacterState.class)
  void handleCharacterCreationPayment_givenCharacterState_checksForValidState(CharacterState state) {
    basicPayment.getPayingParty().setState(state);

    if (state != CharacterState.INIT_COMPLETE) {
      Exception exception = assertThrows(
          IllegalStateException.class, () -> PaymentHandler.handleCharacterCreationPayment(basicPayment));
      assertTrue(exception.getMessage().contains("Character State should be"));
    } else {
      basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);
      PaymentHandler.handleCharacterCreationPayment(basicPayment);
      assertSame(ChangeStatus.APPROVED, basicPayment.getStatus());
      assertEquals(4, basicPayment.getPayingParty().getItems().keySet().size());
    }
  }

  @Test
  void handleCharacterCreationPayment_paymentFundsMismatch_gold() {
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);
    basicPayment.setGold(11); // Set a price higher than available
    PaymentHandler.handleCharacterCreationPayment(basicPayment);
    assertSame(ChangeStatus.REJECTED, basicPayment.getStatus());
    assertSame(ChangeStatusLabel.FUNDING_MISMATCH, basicPayment.getStatusLabel());
  }

  @Test
  void handleCharacterCreationPayment_paymentFundsMismatch_silver() {
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);
    basicPayment.setSilver(101); // Set a price higher than available
    PaymentHandler.handleCharacterCreationPayment(basicPayment);
    assertSame(ChangeStatus.REJECTED, basicPayment.getStatus());
    assertSame(ChangeStatusLabel.FUNDING_MISMATCH, basicPayment.getStatusLabel());
  }

  @Test
  void handleCharacterCreationPayment_paymentFundsMismatch_copper() {
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);
    basicPayment.setCopper(501); // Set a price higher than available
    PaymentHandler.handleCharacterCreationPayment(basicPayment);
    assertSame(ChangeStatus.REJECTED, basicPayment.getStatus());
    assertSame(ChangeStatusLabel.FUNDING_MISMATCH, basicPayment.getStatusLabel());
  }

  @Test
  void testPaymentWithChangeBack(){
    basicPayment.getItemsExchanged().get(0).getItem().setPrice(70.0);
    basicPayment.setSilver(50);
    PaymentHandler.handleCharacterCreationPayment(basicPayment);
    basicPayment.getPayingParty().getAmountOf(Coin.SILVER_PIECE);

  }
}

