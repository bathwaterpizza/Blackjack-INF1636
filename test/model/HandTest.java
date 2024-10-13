package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class HandTest {

    @Test
    public void testAddCard() {
        Hand hand = new Hand();
        Card card = new Card(Suit.SPADES, Rank.ACE);
        hand.addCard(card);
        assertEquals("Hand points should be 11", 11, hand.points);
    }

    @Test
    public void testIsBlackjack() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.ACE));
        hand.addCard(new Card(Suit.HEARTS, Rank.KING));
        assertTrue("Hand should be a blackjack", hand.isBlackjack());
    }

    @Test
    public void testIsBust() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.TEN));
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        hand.addCard(new Card(Suit.CLUBS, Rank.TWO));
        assertTrue("Hand should be bust", hand.isBust());
    }

    @Test
    public void testIsPair() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.TEN));
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        assertTrue("Hand should be a pair", hand.isPair());
    }
}
