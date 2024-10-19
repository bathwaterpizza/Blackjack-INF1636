package model;

class Player {
  private static final double START_BALANCE = 2400.0;

  public Hand hand = new Hand();
  public double balance = START_BALANCE;
  public int bet = 0;

  // adds a chip's value to the bet, removing it from balance
  // returns true if there are enough chips in our balance to add, false otherwise
  public boolean incrementBet(ChipColor chipColor) {
    return incrementBet(chipColor.toInt());
  }

  // overload for an int parameter
  public boolean incrementBet(int betValue) {
    // check if chip is available in player's balance
    if (balance >= betValue) {
      balance -= betValue;
      bet += betValue;

      return true;
    }

    // chip not available in player's balance
    System.out.println("Not enough balance to increment bet.");
    return false;
  }

  // removes a chip's value from the bet, adding it to balance
  // returns true if there are enough chips in our bet to remove, false otherwise
  public boolean decrementBet(ChipColor chipColor) {
    return decrementBet(chipColor.toInt());
  }

  public boolean decrementBet(int betValue) {
    // check if chip is available in player's balance
    if (bet >= betValue) {
      balance += betValue;
      bet -= betValue;

      return true;
    }

    // chip not available in player's balance
    System.out.println("Not enough bet to decrement balance.");
    return false;
  }

  // returns whether hit was successful
  public boolean hit(Card newCard) {
    hand.addCard(newCard);

    return true;
  }

  // returns whether double was successful
  public boolean doubleBet(Card newCard) {
    // check if there's enough balance to double
    if (balance < bet) {
      return false;
    }

    // double bet and hit
    balance -= bet;
    bet *= 2;
    this.hit(newCard);

    return true;
  }

  // put the value in bet back to balance, and clear bet.
  // called when game ties
  public void receiveTiePayout() {
    assert bet > 0;

    // transfer value to balance
    balance += bet;
    bet = 0;
  }

  // put twice the value in bet back to balance, and clear bet.
  // called when the player wins
  public void receiveWinPayout() {
    // copy twice of bet to balance
    assert bet > 0;

    // copy double the bet value to balance
    balance += 2 * bet;
    bet = 0;
  }

  // put half the value in bet back to balance, and clear bet.
  // called when the player surrenders
  public void receiveHalfPayout() {
    // copy half of bet to balance
    assert bet > 0;

    // copy half the bet value
    balance += bet / 2;
    bet = 0;
  }

  public void split() {
    // TODO: Next iterations
  }
}
