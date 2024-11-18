package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
  private Game game;

  @Before
  public void setUp() {
    game = Game.getAPI();
    game.choiceStand();
    game.choiceClear();
    game.player.balance = 2400.0;
  }

  /**
   * Test that a bet less than the minimum bet is not accepted.
   */
  @Test
  public void testBetUnaccepted() {
    game.player.bet = 25;
    boolean result = game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TWO),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    assertFalse("The bet wasn't high enough to be placed", result);
  }

  /**
   * Test that a bet equal to or greater than the minimum bet is accepted.
   */
  @Test
  public void testBetAccepted() {
    game.player.bet = 50;
    boolean result = game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TWO),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    assertTrue("The bet was placed", result);
  }

  /**
   * Test that hitting on the split hand works correctly.
   */
  @Test
  public void testChoiceHitSplitHand() {
	game.roundOver = false;
    game.player.bet = 50;
    game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TEN),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    game.choiceSplit();
    game.choiceHit();
    boolean result = game.choiceHit();
    assertTrue("Hit on split hand should be successful", result);
  }

  /**
   * Test that doubling down on the split hand works correctly.
   */
  @Test
  public void testChoiceDoubleSplitHand() {
    game.roundOver = false;
    game.player.balance = 2400.0;
    game.player.incrementBet(50);
    game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TEN),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    game.choiceSplit();
    boolean result = game.choiceDouble();
    assertTrue("Double down on split hand should be successful", result);
  }

  /**
   * Test that standing on the split hand works correctly.
   */
  @Test
  public void testChoiceStandSplitHand() {
	game.roundOver = false;
    game.player.bet = 50;
    game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TEN),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    game.choiceSplit();
    boolean result = game.choiceStand();
    assertTrue("Stand on split hand should be successful", result);
  }

  /**
   * Test that surrendering on the main hand works correctly.
   */
  @Test
  public void testChoiceSurrenderMainHand() {
	game.choiceStand();
	game.choiceClear();
	game.roundOver = false;
    game.player.bet = 50;
    game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TWO),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    boolean result = game.choiceSurrender();
    assertTrue("Surrender on main hand should be successful", result);
  }

  /**
   * Test that surrendering on the split hand is not allowed.
   */
  @Test
  public void testChoiceSurrenderSplitHand() {
    game.player.bet = 50;
    game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TEN),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    game.choiceSplit();
    boolean result = game.choiceSurrender();
    assertFalse("Surrender on split hand should not be allowed", result);
  }

  /**
   * Test that splitting a pair works correctly.
   */
  @Test
  public void testChoiceSplit() {
    game.roundOver = false;
    game.player.bet = 50;
    game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TEN),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    boolean result = game.choiceSplit();
    assertTrue("Splitting a pair should be successful", result);
  }

  /**
   * Test that decrementing the bet works correctly.
   */
  @Test
  public void testChoiceDecBet() {
    game.choiceIncBet(Chip.BLUE);
    boolean result = game.choiceDecBet(Chip.BLUE);
    assertTrue("Decrementing the bet should be successful", result);
  }

  /**
   * Test that clearing the game state works correctly.
   */
  @Test
  public void testChoiceClear() {
    game.player.bet = 50;
    game.choiceDeal(new Card(Suit.HEARTS, Rank.TEN), new Card(Suit.CLUBS, Rank.TWO),
        new Card(Suit.SPADES, Rank.THREE), new Card(Suit.DIAMONDS, Rank.FOUR));
    boolean result = game.choiceClear();
    assertTrue("Clearing the game state should be successful", result);
  }
}
