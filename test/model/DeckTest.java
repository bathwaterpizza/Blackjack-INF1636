package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class DeckTest {
  @Test
  public void testDeckComplete() {
    Deck deck = new Deck(false);
    assertEquals("The deck is complete", 416, deck.getDeckSize());
  }

  @Test
  public void testShouldntReshuffle() {
    // identicals decks
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

  @Test
  public void testShouldReshuffle() {
    // identicals decks
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
