package model;

class Dealer {
  public Hand hand = new Hand();

  public void hit(Card newCard) {
    hand.addCard(newCard);
  }
}
