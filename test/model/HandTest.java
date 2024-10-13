package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HandTest {
  private Hand hand;

  @Before
  public void setUp() {
    hand = new Hand();
  }

  @Test
  public void testAddCard() {
    // Test adding a card to the hand and updating points
    Card card = new Card(Suit.HEARTS, Rank.TEN);
    hand.addCard(card);
    assertEquals(1, hand.cards.size());
    assertEquals(10, hand.points);
  }

  @Test
  public void testUpdatePointsWithAce() {
    // Test updating points when an Ace is added, considering its value as 11
    Card ace = new Card(Suit.SPADES, Rank.ACE);
    Card nine = new Card(Suit.HEARTS, Rank.NINE);
    hand.addCard(ace);
    hand.addCard(nine);
    assertEquals(20, hand.points);
  }

  @Test
  public void testUpdatePointsWithoutAce() {
    // Test updating points when no Ace is present
    Card ten = new Card(Suit.HEARTS, Rank.TEN);
    Card nine = new Card(Suit.CLUBS, Rank.NINE);
    hand.addCard(ten);
    hand.addCard(nine);
    assertEquals(19, hand.points);
  }

  @Test
  public void testIsBlackjack() {
    // Test if the hand is a blackjack (Ace and a 10-value card)
    Card ace = new Card(Suit.SPADES, Rank.ACE);
    Card ten = new Card(Suit.HEARTS, Rank.TEN);
    hand.addCard(ace);
    hand.addCard(ten);
    assertTrue(hand.isBlackjack());
  }

  @Test
  public void testIsNotBlackjack() {
    // Test if the hand is not a blackjack
    Card ace = new Card(Suit.SPADES, Rank.ACE);
    Card nine = new Card(Suit.HEARTS, Rank.NINE);
    hand.addCard(ace);
    hand.addCard(nine);
    assertFalse(hand.isBlackjack());
  }

  @Test
  public void testIsBust() {
    // Test if the hand is a bust (points exceed 21)
    Card ten = new Card(Suit.HEARTS, Rank.TEN);
    Card nine = new Card(Suit.CLUBS, Rank.NINE);
    Card five = new Card(Suit.DIAMONDS, Rank.FIVE);
    hand.addCard(ten);
    hand.addCard(nine);
    hand.addCard(five);
    assertTrue(hand.isBust());
  }

  @Test
  public void testIsNotBust() {
    // Test if the hand is not a bust (points do not exceed 21)
    Card ten = new Card(Suit.HEARTS, Rank.TEN);
    Card nine = new Card(Suit.CLUBS, Rank.NINE);
    hand.addCard(ten);
    hand.addCard(nine);
    assertFalse(hand.isBust());
  }

  @Test
  public void testIsPair() {
    // Test if the hand is a pair (two cards of the same rank)
    Card ten1 = new Card(Suit.HEARTS, Rank.TEN);
    Card ten2 = new Card(Suit.CLUBS, Rank.TEN);
    hand.addCard(ten1);
    hand.addCard(ten2);
    assertTrue(hand.isPair());
  }

  @Test
  public void testIsNotPair() {
    // Test if the hand is not a pair (two cards of different ranks)
    Card ten = new Card(Suit.HEARTS, Rank.TEN);
    Card nine = new Card(Suit.CLUBS, Rank.NINE);
    hand.addCard(ten);
    hand.addCard(nine);
    assertFalse(hand.isPair());
  }
}
