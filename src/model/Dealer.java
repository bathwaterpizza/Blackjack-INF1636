package model;

class Dealer {
  public Hand hand = new Hand();

  public boolean hit(Card newCard) {
    hand.addCard(newCard);

    return true;
  }
}
