import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Hand;
import model.Card;
import model.Rank;
import model.Suit;

public class HandTest {
    private Hand hand;

    @Before
    public void setUp() {
        hand = new Hand();
    }

    @Test
    public void testAddCard() {
        Card card = new Card(Rank.TEN, Suit.HEARTS);
        hand.addCard(card);
        assertEquals(1, hand.getCards().size());
        assertEquals(10, hand.getPoints());
    }

    @Test
    public void testIsBlackjack() {
        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.KING, Suit.CLUBS));
        assertTrue(hand.isBlackjack());
    }

    @Test
    public void testIsBust() {
        hand.addCard(new Card(Rank.TEN, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.TEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.TWO, Suit.SPADES));
        assertTrue(hand.isBust());
    }

    @Test
    public void testIsPair() {
        hand.addCard(new Card(Rank.FIVE, Suit.HEARTS));
        hand.addCard(new Card(Rank.FIVE, Suit.DIAMONDS));
        assertTrue(hand.isPair());
    }
}
