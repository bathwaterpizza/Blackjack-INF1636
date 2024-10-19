package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

  @Test
  public void testBetUnaccepted() {
    Game.player.bet = 25;
    Game.choiceDeal();
    assertFalse("The bet wasn't high enough to be placed", Game.betPlaced);

  }

  @Test
  public void testBetAccepted() {
    Game.player.bet = 50;
    Game.choiceDeal();
    assertTrue("The bet was placed", Game.betPlaced);

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
