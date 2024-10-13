package model;

class Dealer {
  public Hand currentHand;

  public boolean hit(Card newCard) {
    currentHand.addCard(newCard);

    return true;
  }
}
