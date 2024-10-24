package model;

class Dealer {
  Hand hand = new Hand();

  void hit(Card newCard) {
    hand.addCard(newCard);
  }
}
