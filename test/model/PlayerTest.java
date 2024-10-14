package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testIncrementBetGoldFail() {
		Player player = new Player();
		
		for (int i = 0; i < 10; i++) {
			 player.incrementBet(ChipColor.GOLD);
		}
		boolean status = player.incrementBet(ChipColor.GOLD);
		assertFalse("Insufficient Balance", status);
	}
	
	@Test
	public void testIncrementBetGoldSucess() {
		Player player = new Player();
		
		for (int i = 0; i < 5; i++) {
			 player.incrementBet(ChipColor.GOLD);
		}
		boolean status = player.incrementBet(ChipColor.GOLD);
		assertTrue("Sufficient Balance", status);
	}
	
	@Test
    public void testDoubleBetWithSufficientBalance() {
        Player player = new Player();
        Card placeholder_card = new Card(Suit.HEARTS, Rank.ACE);
        Hand placeholder_hand = new Hand();
        player.currentHand = placeholder_hand;
        player.incrementBet(ChipColor.BLUE); 
        boolean status = player.doubleBet(placeholder_card);
        assertTrue("Doublet Bet Sucessful", status);
    }
	
	@Test
    public void testDoubleBetWithInsufficientBalance() {
        Player player = new Player();
        Card placeholder_card = new Card(Suit.HEARTS, Rank.ACE);
        Hand placeholder_hand = new Hand();
        player.currentHand = placeholder_hand;
        
        //creates a bet that is half of his balance + 5 (unable to double it)
        for(int i = 0; i<6;i++) {
        	player.incrementBet(ChipColor.GREEN); 
        	player.incrementBet(ChipColor.GOLD);
        }
        for(int i = 0; i<11;i++) {
        	player.incrementBet(ChipColor.RED); 
        }
        for(int i = 0; i<26;i++) {
        	player.incrementBet(ChipColor.BLUE); 
        }
        
        boolean status = player.doubleBet(placeholder_card);
        assertFalse("Double Bet Uncessful", status);
    }
	
	@Test
    public void testBalanceAfterTie() {
		Player player = new Player();
		int balanceBefore = player.balance;
		player.incrementBet(ChipColor.GREEN);
		player.receiveTiePayout();
		int balanceAfter = player.balance;
		assertEquals("The player had his bet returned to the balance",balanceBefore,balanceAfter);
		
	}
	
	@Test
	public void testBalanceAfterWin() {
		Player player = new Player();
		int balanceBefore = player.balance;
		player.incrementBet(ChipColor.GREEN);
		int betValue= Chip.getColorValue(ChipColor.GREEN);
		player.receiveWinPayout();
		int balanceAfter = player.balance;
		assertEquals("The player had his bet doubled",2400-betValue+(2*betValue),balanceAfter);
		
	}
	
	@Test
    public void testBalanceAfter() {
		Player player = new Player();
		int balanceBefore = player.balance;
		player.incrementBet(ChipColor.GOLD);
		int betValue = Chip.getColorValue(ChipColor.GOLD);
		player.receiveHalfPayout();
		int balanceAfter = player.balance;
		
		assertEquals("The player had his bet returned to the balance",balanceBefore-betValue+(betValue/2),balanceAfter);
		
	}
	
	@Test
    public void testBetIsClear() {
		Player player = new Player();
		player.incrementBet(ChipColor.BLUE);
		assertFalse(player.betChips.isEmpty());
		player.clearBet();
		assertTrue(player.betChips.isEmpty());	
	}
	
	
}
	
	
