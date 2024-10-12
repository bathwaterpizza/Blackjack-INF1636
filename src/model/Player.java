package model;

import java.util.ArrayList;

class Player {
  public Hand currentHand = new Hand();
  public int balance = 2400;
  public int bet = 0;
  public ArrayList<Chip> balanceChips = new ArrayList<>();
  public ArrayList<Chip> betChips = new ArrayList<>();

  public Player() {
    // populate chips equivalent to 2400 as instructed
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
  // returns true if there was a chip in our balanced to add, false otherwise
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

    return false;
  }

  public void stand() {
    // a
  }

  public void hit() {
    // a
  }

  public void doubleBet() {
    // a
  }

  public void split() {
    // TODO: Next iteration
  }

  public void surrender() {
    // TODO: Ask Ivan how this should work
  }
}
