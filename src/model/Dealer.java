package model;

// represents the dealer,
// this class exists mostly to make the model code more uniform
class Dealer {
  Hand hand = new Hand();

  void hit(Card newCard) {
    hand.addCard(newCard);
  }
}
