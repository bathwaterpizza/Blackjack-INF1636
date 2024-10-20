package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

  @Test
  public void testIncrementBetGoldFail() {
    Player player = new Player();

    for (int i = 0; i < 25; i++) {
      player.incrementBet(Chip.GOLD);
    }
    boolean status = player.incrementBet(Chip.GOLD);
    assertFalse("Insufficient Balance", status);
  }

  @Test
  public void testIncrementBetGoldSucess() {
    Player player = new Player();

    for (int i = 0; i < 5; i++) {
      player.incrementBet(Chip.GOLD);
    }
    boolean status = player.incrementBet(Chip.GOLD);
    assertTrue("Sufficient Balance", status);
  }

  @Test
  public void testDoubleBetWithSufficientBalance() {
    Player player = new Player();
    Card placeholder_card = new Card(Suit.HEARTS, Rank.ACE);
    Hand placeholder_hand = new Hand();
    player.hand = placeholder_hand;
    player.incrementBet(Chip.BLUE);
    boolean status = player.doubleBet(false, placeholder_card);
    assertTrue("Doublet Bet Sucessful", status);
  }

  @Test
  public void testDoubleBetWithInsufficientBalance() {
    Player player = new Player();
    Card placeholder_card = new Card(Suit.HEARTS, Rank.ACE);
    Hand placeholder_hand = new Hand();
    player.hand = placeholder_hand;

    // creates a bet that is half of his balance + 5 (unable to double it)
    for (int i = 0; i < 6; i++) {
      player.incrementBet(Chip.GREEN);
      player.incrementBet(Chip.GOLD);
    }
    for (int i = 0; i < 11; i++) {
      player.incrementBet(Chip.RED);
    }
    for (int i = 0; i < 26; i++) {
      player.incrementBet(Chip.BLUE);
    }

    boolean status = player.doubleBet(false, placeholder_card);
    assertFalse("Double Bet Uncessful", status);
  }

  @Test
  public void testBalanceAfterTie() {
    Player player = new Player();
    double balanceBefore = player.balance;
    player.incrementBet(Chip.GREEN);
    player.receiveTiePayout(false);
    double balanceAfter = player.balance;
    assertEquals("The player had his bet returned to the balance", balanceBefore, balanceAfter);
  }

  @Test
  public void testBalanceAfterWin() {
    Player player = new Player();
    player.incrementBet(Chip.GREEN);
    int betValue = Chip.GREEN.toInt();
    player.receiveWinPayout(false);
    double balanceAfter = player.balance;
    assertEquals("The player had his bet doubled", 2400 - betValue + (2 * betValue), balanceAfter);

  }

  @Test
  public void testBalanceAfter() {
    Player player = new Player();
    double balanceBefore = player.balance;
    player.incrementBet(Chip.GOLD);
    int betValue = Chip.GOLD.toInt();
    player.receiveHalfPayout(false);
    double balanceAfter = player.balance;

    assertEquals("The player had his bet returned to the balance", balanceBefore - betValue + (betValue / 2),
        balanceAfter);

  }

  @Test
  public void testBetIsClear() {
    Player player = new Player();
    player.incrementBet(Chip.BLUE);
    assertFalse(player.bet == 0);
    player.bet = 0;
    assertTrue(player.bet == 0);
  }

}
