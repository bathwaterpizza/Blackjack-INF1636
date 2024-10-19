package model;

class Dealer {
  public Hand currentHand = new Hand();

  public boolean hit(Card newCard) {
    currentHand.addCard(newCard);

    return true;
  }
}
