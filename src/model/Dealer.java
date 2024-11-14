package model;

import java.io.Serializable;

// represents the dealer,
// this class exists mostly to make the model code more uniform
class Dealer implements Serializable {
  Hand hand = new Hand();

  void hit(Card newCard) {
    hand.addCard(newCard);
  }
}
