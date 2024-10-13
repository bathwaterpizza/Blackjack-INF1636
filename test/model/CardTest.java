package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

    @Test
    public void testValueAce() {
        Card card = new Card(Suit.CLUBS, Rank.ACE);
        assertEquals(1, card.getValue());
    }

    @Test
    public void testValueFive() {
        Card card = new Card(Suit.DIAMONDS, Rank.FIVE);
        assertEquals(5, card.getValue());
    }

    @Test
    public void testValueTen() {
        Card card = new Card(Suit.HEARTS, Rank.TEN);
        assertEquals(10, card.getValue());
    }

    @Test
    public void testValueQueen() {
        Card card = new Card(Suit.SPADES, Rank.QUEEN);
        assertEquals(10, card.getValue());
    }

    @Test
    public void testValueJack() {
        Card card = new Card(Suit.SPADES, Rank.JACK);
        assertEquals(10, card.getValue());
    }

    @Test
    public void testEqualValue() {
        Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card2 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        assertTrue(card1.equals(card2));
    }

    @Test
    public void testDifferentSuit() {
        Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card2 = new Card(Suit.HEARTS, Rank.SEVEN);
        assertFalse(card1.equals(card2));
    }

    @Test
    public void testDifferentRank() {
        Card card1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card2 = new Card(Suit.DIAMONDS, Rank.EIGHT);
        assertFalse(card1.equals(card2));
    }
}
