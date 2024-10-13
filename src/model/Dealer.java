package model;

class Dealer {
  public Hand currentHand = new Hand();

  public void hit() {
    Card newCard = Game.deck.getCard();
    currentHand.addCard(newCard);
  }
}
