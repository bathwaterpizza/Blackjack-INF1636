package model;

import java.util.ArrayList;

class Player {
  public Hand currentHand;
  public int balance = 2400;
  public int bet = 0;
  public ArrayList<Chip> balanceChips = new ArrayList<>();
  public ArrayList<Chip> betChips = new ArrayList<>();

  public Player() {
    // start game with $2400 in chips
    for (int i = 0; i < 10; i++) {
      balanceChips.add(new Chip(ChipColor.GOLD));
      balanceChips.add(new Chip(ChipColor.GREEN));
    }
    for (int i = 0; i < 20; i++) {
      balanceChips.add(new Chip(ChipColor.RED));
    }
    for (int i = 0; i < 50; i++) {
      balanceChips.add(new Chip(ChipColor.BLUE));
    }
  }

  // adds a chip to the pending bet value.
  // returns true if there was a chip in our balance to add, false otherwise
  public boolean incrementBet(ChipColor chipColor) {
    for (Chip chip : balanceChips) {
      if (chip.value == Chip.getColorValue(chipColor)) {
        System.out.println("Adding " + chipColor + " chip to bet.");
        // increment chips
        betChips.add(chip);
        balanceChips.remove(chip);

        // increment value
        balance -= chip.value;
        bet += chip.value;

        return true;
      }
    }

    // chip not available in player's balance
    System.out.println("You don't have any " + chipColor + " chips to bet.");
    return false;
  }

  // returns whether hit was successful
  public boolean hit(Card newCard) {
    currentHand.addCard(newCard);

    return true;
  }

  // returns whether double was successful
  public boolean doubleBet(Card newCard) {
    // check if there's enough balance to double
    if (balance - bet < 0) {
      return false;
    }

    // duplicate bet value and hit
    var betCopy = new ArrayList<>(betChips);

    for (Chip c : betCopy) {
      this.incrementBet(c.color);
    }

    this.hit(newCard);

    return true;
  }

  // put the value in bet back to balance, and clear bet.
  // called when game ties
  public void receiveTiePayout() {
    assert bet > 0;

    // transfer chips to balance
    for (Chip c : betChips) {
      balanceChips.add(c);
    }

    // transfer value to balance
    balance += bet;

    this.clearBet();
  }

  // put twice the value in bet back to balance, and clear bet.
  // called when the player wins
  public void receiveWinPayout() {
    // copy twice of bet to balance
    assert bet > 0;

    // copy bet chips to balance twice
    for (Chip c : betChips) {
      balanceChips.add(c);
      balanceChips.add(c);
    }

    // copy double the bet value to balance
    balance += 2 * bet;

    this.clearBet();
  }

  // put half the value in bet back to balance, and clear bet.
  // called when the player surrenders
  public void receiveHalfPayout() {
    // copy half of bet to balance
    assert bet > 0;

    // TODO: Ask Ivan about chips

    // copy half the bet value
    balance += bet / 2;

    this.clearBet();
  }

  // clear bet value and chips.
  // called when player loses, and within the other payout methods
  public void clearBet() {
    betChips.clear();
    bet = 0;
  }

  public void split() {
    // TODO: Next iterations
  }

  public void surrender() {
    // TODO: Ask Ivan about chips
  }
}
