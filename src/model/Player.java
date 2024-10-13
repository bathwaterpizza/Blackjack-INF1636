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
        betChips.add(chip);
        balanceChips.remove(chip);

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
  public boolean hit() {
    // check if can hit
    if (!Game.betPlaced || Game.roundOver) {
      return false;
    }

    Card newCard = Game.deck.getCard();
    currentHand.addCard(newCard);

    return true;
  }

  // returns whether double was successful
  public boolean doubleBet() {
    // check if can double
    if (!Game.betPlaced || Game.roundOver || Game.doubled) {
      return false;
    }
    // check if there's enough balance to double
    if (balance - bet < 0) {
      return false;
    }

    // duplicate bet value and hit
    var betCopy = new ArrayList<>(betChips);

    for (Chip c : betCopy) {
      this.incrementBet(c.color);
    }

    this.hit();

    return true;
  }

  public void split() {
    // TODO: Next iteration
  }

  public void surrender() {
    // TODO: Ask Ivan about chips
  }
}
