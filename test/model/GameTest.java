package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {
  private Game game = Game.getAPI();

  @Test
  public void testBetUnaccepted() {
    game.player.bet = 25;
    game.choiceDeal();
    assertFalse("The bet wasn't high enough to be placed", game.betPlaced);

  }

  @Test
  public void testBetAccepted() {
    game.player.bet = 50;
    game.choiceDeal();
    assertTrue("The bet was placed", game.betPlaced);

  }

  // @Test
  // public void testNewRound() {
  // Game game = new Game();
  // game.player.bet = 50;
  // game.makeBet();
  // assertTrue("The bet was placed", game.betPlaced);
  // game.choiceNewRound();
  // assertTrue("The bet needs to be placed again", game.betPlaced);
  //
  // }
}
