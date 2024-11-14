package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Stack;

// represents the deck of available cards for drawing in the game,
// refills and shuffles automatically after 10% of the deck is used
class Deck implements Serializable {
  private static final int DRAW_LIMIT = 41;
  private static final int TOTAL_CARDS = 416;

  private Stack<Card> deck;

  // shouldShuffle: true to shuffle on creation, used for testing.
  Deck(boolean shouldShuffle) {
    // create a deck with eight sets of 52 cards
    createNewDeck();

    if (shouldShuffle) {
      Collections.shuffle(deck);
    }
  }

  // creates a new deck with 8 sets of 52 cards
  private void createNewDeck() {
    deck = new Stack<>();

    for (int i = 0; i < 8; i++) {
      for (Suit suit : Suit.values()) {
        for (Rank rank : Rank.values()) {
          deck.push(new Card(suit, rank));
        }
      }
    }
  }

  // called after every round to check if we need to reshuffle
  void tryReshuffle() {
    if (deck.size() <= TOTAL_CARDS - DRAW_LIMIT) {
      createNewDeck();
      Collections.shuffle(deck);
    }
  }

  // returns the card at the top of the deck, also removing it from the deck
  Card getCard() {
    if (deck.isEmpty()) {
      System.out.println("Error: getCard from empty deck");
      return null;
    }

    return deck.pop();
  }

  // inserts a card at the top of the deck
  void putCard(Card card) {
    deck.push(card);
  }

  // returns the amount of cards in the deck
  int getDeckSize() {
    return deck.size();
  }
}
