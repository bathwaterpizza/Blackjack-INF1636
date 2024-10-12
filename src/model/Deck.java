package model;

import java.util.Collections;
import java.util.Stack;

class EmptyDeckException extends RuntimeException {
  public EmptyDeckException(String message) {
    super(message);
  }
}

class Deck {
  private static final int DRAW_LIMIT = 41;
  private static final int TOTAL_CARDS = 416;
  private Stack<Card> deck;

  // shouldShuffle: true to shuffle on creation
  public Deck(boolean shouldShuffle) {
    // create a deck with eight sets of 52 cards
    createNewDeck();

    if (shouldShuffle)
      shuffle();
  }

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

  public void tryReshuffle() {
    if (deck.size() <= TOTAL_CARDS - DRAW_LIMIT) {
      createNewDeck();
      shuffle();
    }
  }

  public void shuffle() {
    Collections.shuffle(deck);
  }

  public Card getCard() {
    if (deck.isEmpty()) {
      throw new EmptyDeckException("Deck is empty");
    }

    return deck.pop();
  }

  public void putCard(Card card) {
    deck.push(card);
  }
}
