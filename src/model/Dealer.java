package model;

class Dealer {
  public Hand currentHand;

  public void hit() {
    Card newCard = Game.deck.getCard();
    currentHand.addCard(newCard);
  }
}
