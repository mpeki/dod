package dk.dodgame.util.item;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterItemDTO;
import dk.dodgame.data.ItemDTO;
import dk.dodgame.data.PaymentDTO;
import dk.dodgame.domain.changerequest.model.ChangeStatus;
import dk.dodgame.domain.changerequest.model.ChangeStatusLabel;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.item.model.Coin;
import dk.dodgame.util.rules.ArmorRules;

import java.util.ArrayList;
import java.util.List;

public class PaymentHandler {

  private PaymentHandler() {
  }

  public static PaymentDTO setPayingParty(PaymentDTO paymentItem, CharacterDTO payingParty){
    paymentItem.setPayingParty(payingParty);
    return paymentItem;
  }
  public static PaymentDTO setPaymentItems(PaymentDTO payment, ItemDTO itemToBuy, int quantity) {
    if(payment == null){
      throw new PaymentException("No payment received!");
    }
    if(payment.getPayingParty() == null){
      payment.setStatus(ChangeStatus.REJECTED);
      payment.setStatusLabel(ChangeStatusLabel.NO_PAYING_PARTY);
      return payment;
    }

    CharacterDTO character = payment.getPayingParty();
    List<CharacterItemDTO> charItems = new ArrayList<>();
    if(quantity > 0){
      for (int i = 0; i < quantity; i++) {

        Double currentPrice = itemToBuy.getWeightReference() != null ?
                ArmorRules.getArmorPriceForSize(character.getBaseTraitValue(BaseTraitName.SIZE), itemToBuy) :
                itemToBuy.getPrice();

        Double currentWeight = itemToBuy.getWeightReference() != null ?
                ArmorRules.getArmorWeightForCharSize(character.getBaseTraitValue(BaseTraitName.SIZE), itemToBuy.getWeightReference().charAt(0)) :
                itemToBuy.getWeight();

        charItems.add(CharacterItemDTO.builder().currentWeight(currentWeight).currentPrice(currentPrice)
                .item(itemToBuy).build());
      }
      payment.setItemsExchanged(charItems);
    }
    return payment;
  }

  public static PaymentDTO handleCharacterCreationPayment(PaymentDTO payment) {

    if (payment == null) {
      throw new PaymentException("Payment is null!");
    }
    CharacterDTO payingParty = payment.getPayingParty();
    if (payingParty == null) {
      payment.setStatus(ChangeStatus.REJECTED);
      payment.setStatusLabel(ChangeStatusLabel.NO_PAYING_PARTY);
      return payment;
    }
    if (payment.getPayingParty().getState() != CharacterState.INIT_COMPLETE) {
      throw new IllegalStateException(
          "Character State should be: " + CharacterState.INIT_COMPLETE + " but was: " + payingParty.getState());
    }

    doFundsCheck(payment);

    if(payment.getStatus() != ChangeStatus.REJECTED){
      return doPayment(payment);
    } else {
      return payment;
    }
  }

  private static PaymentDTO doPayment(PaymentDTO payment){

    CharacterDTO payingParty = payment.getPayingParty();
    payingParty.subtractCoins(payment.getGold(), Coin.GOLD_PIECE);
    payingParty.subtractCoins(payment.getSilver(), Coin.SILVER_PIECE);
    payingParty.subtractCoins(payment.getCopper(), Coin.COPPER_PIECE);
    giveBackChange(payment);
    for (CharacterItemDTO item : payment.getItemsExchanged()) {
      payingParty.addItem(item);
    }
    payment.setStatus(ChangeStatus.APPROVED);
    return payment;
  }

  private static void giveBackChange(PaymentDTO payment){
    CharacterDTO payingParty = payment.getPayingParty();

    long silverPayed = getAmountPayedInSilver(payment);
    double silverPrice = getPriceInSilver(payment);
    if ( silverPayed > silverPrice) {
      double changeAmount = silverPayed - silverPrice;
      int goldRefund = changeAmount > 10 ? (int)Math.floor(changeAmount / 10) : 0;
      changeAmount = changeAmount - (goldRefund * 10);
      int silverRefund = (int)Math.floor(changeAmount);
      int copperRefund = changeAmount % 1 == 0 ? 0 : (int)Math.ceil((changeAmount - silverRefund) * 10);
      payingParty.addCoins(goldRefund, Coin.GOLD_PIECE);
      payingParty.addCoins(silverRefund, Coin.SILVER_PIECE);
      payingParty.addCoins(copperRefund, Coin.COPPER_PIECE);

    }
  }

  private static void doFundsCheck(PaymentDTO payment){

    if(payment.getPayingParty().getAmountOf(Coin.GOLD_PIECE) < payment.getGold() ||
        payment.getPayingParty().getAmountOf(Coin.SILVER_PIECE) < payment.getSilver() ||
        payment.getPayingParty().getAmountOf(Coin.COPPER_PIECE) < payment.getCopper() ){
      payment.setStatus(ChangeStatus.REJECTED);
      payment.setStatusLabel(ChangeStatusLabel.FUNDING_MISMATCH);
    }
    double priceInSilver = getPriceInSilver(payment);
    if(payment.getStatus() == ChangeStatus.REJECTED){
      return;
    }
    long amountPayedInSilver = getAmountPayedInSilver(payment);
    if(amountPayedInSilver < priceInSilver){
      payment.setStatus(ChangeStatus.REJECTED);
      payment.setStatusLabel(ChangeStatusLabel.INSUFFICIENT_FUNDS);
    }
  }

  private static long getAmountPayedInSilver(PaymentDTO payment){
      return (payment.getGold() * 10L) + payment.getSilver() + (payment.getCopper() / 10);
  }

  private static double getPriceInSilver(PaymentDTO payment) {
    List<CharacterItemDTO> itemsBought = payment.getItemsExchanged();
    if( itemsBought == null || itemsBought.isEmpty() ){
      payment.setStatus(ChangeStatus.REJECTED);
      payment.setStatusLabel(ChangeStatusLabel.NO_ITEMS_IN_PAYMENT);
      return 0.0;
    }
    double totalPrice = 0.0;
    for (CharacterItemDTO itemBought : itemsBought) {
      if (itemBought.getCurrentPrice() != null) {
        totalPrice += itemBought.getCurrentPrice();
      } else if (itemBought.getItem().getPrice() != null) {
        totalPrice += itemBought.getItem().getPrice();
      } else {
        payment.setStatus(ChangeStatus.REJECTED);
        payment.setStatusLabel(ChangeStatusLabel.NO_PRICE_CALCULATED);
      }
    }
    return totalPrice;
  }
}
