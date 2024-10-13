package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class CardTest {
  @Test
  // Test that the value of an Ace card is 1
  public void testValueAce() {
    Card card = new Card(Suit.CLUBS, Rank.ACE);
    assertEquals(1, card.getValue());
  }

  @Test
  // Test that the value of a Five card is 5
  public void testValueFive() {
    Card card = new Card(Suit.DIAMONDS, Rank.FIVE);
    assertEquals(5, card.getValue());
  }

  @Test
  // Test that the value of a Ten card is 10
  public void testValueTen() {
    Card card = new Card(Suit.HEARTS, Rank.TEN);
    assertEquals(10, card.getValue());
  }

  @Test
  // Test that the value of a Queen card is 10
  public void testValueQueen() {
    Card card = new Card(Suit.SPADES, Rank.QUEEN);
    assertEquals(10, card.getValue());
  }

  @Test
  // Test that the value of a Jack card is 10
  public void testValueJack() {
    Card card = new Card(Suit.SPADES, Rank.JACK);
    assertEquals(10, card.getValue());
  }

  @Test
  // Test that two cards with the same suit and rank are equal
  public void testEqualValue() {
    Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
    Card card2 = new Card(Suit.DIAMONDS, Rank.SEVEN);
    assertTrue(card1.equals(card2));
  }

  @Test
  // Test that a card is equal to itself
  public void testEqualItself() {
    Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
    assertTrue(card1.equals(card1));
  }

  @Test
  // Test that two cards with the same rank but different suits are not equal
  public void testDifferentSuit() {
    Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
    Card card2 = new Card(Suit.HEARTS, Rank.SEVEN);
    assertFalse(card1.equals(card2));
  }

  @Test
  // Test that two cards with the same suit but different ranks are not equal
  public void testDifferentRank() {
    Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
    Card card2 = new Card(Suit.DIAMONDS, Rank.EIGHT);
    assertFalse(card1.equals(card2));
  }

  @Test
  // Test that a card is not equal to null
  public void testDifferentNull() {
    Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
    Card card2 = null;
    assertFalse(card1.equals(card2));
  }

  @Test
  // Test that a card is not equal to an object of a different class
  public void testDifferentClass() {
    Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
    Deck deck = new Deck(true);
    assertFalse(card1.equals(deck));
  }
}
