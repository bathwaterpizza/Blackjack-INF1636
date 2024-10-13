package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class DeckTest {

  /**
   * Test to verify that a newly created deck contains the correct number of cards
   * (416).
   */
  @Test
  public void testDeckComplete() {
    Deck deck = new Deck(false);
    assertEquals("The deck is complete", 416, deck.getDeckSize());
  }

  /**
   * Test to ensure that the deck does not reshuffle when it is full.
   * Compares two identical decks to confirm they remain the same after a
   * reshuffle attempt.
   */
  @Test
  public void testShouldntReshuffle() {
    Deck deck1 = new Deck(false);
    Deck deck2 = new Deck(false);

    boolean isDiferent = false;

    deck1.tryReshuffle();

    for (int i = 0; i < 416; i++) {
      Card deckCard1 = deck1.getCard();
      Card deckCard2 = deck2.getCard();
      if (!(deckCard1.equals(deckCard2))) {
        isDiferent = true;
        break;
      }
    }

    assertFalse("The deck didn't reshuffle", isDiferent);
  }

  /**
   * Test to ensure that the deck reshuffles when a significant number of cards
   * have been drawn.
   * Compares two identical decks to confirm they differ after a reshuffle.
   */
  @Test
  public void testShouldReshuffle() {
    Deck deck1 = new Deck(false);
    Deck deck2 = new Deck(false);

    boolean isDiferent = false;

    for (int i = 0; i < 42; i++) {
      deck1.getCard();
    }

    deck1.tryReshuffle();

    for (int j = 0; j < 417; j++) {
      Card deckCard1 = deck1.getCard();
      Card deckCard2 = deck2.getCard();
      if (!(deckCard1.equals(deckCard2))) {
        isDiferent = true;
        break;
      }
    }

    assertTrue("The deck did reshuffle", isDiferent);
  }

  /**
   * Test to verify that an exception is thrown when attempting to draw a card
   * from an empty deck.
   */
  @Test
  public void testDeckIsEmpty() {
    Deck deck = new Deck(false);
    try {
      for (int i = 0; i < 418; i++) {
        deck.getCard();
      }
    } catch (EmptyDeckException e) {
      assertEquals("Deck is empty", e.getMessage());
    }
  }

  /**
   * Test to ensure that two decks of different sizes are correctly identified as
   * having different sizes.
   */
  @Test
  public void testDecksDifferentSize() {
    Deck deck1 = new Deck(false);
    Deck deck2 = new Deck(false);
    for (int i = 0; i < 5; i++) {
      deck1.getCard();
    }

    assertTrue("The decks have different sizes", !(deck1.getDeckSize() == deck2.getDeckSize()));
  }
}
