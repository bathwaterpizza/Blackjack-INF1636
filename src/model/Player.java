package model;

class Player {
  private static final double START_BALANCE = 2400.0;

  // player properties
  public double balance = START_BALANCE;

  // main hand properties
  public Hand hand = new Hand();
  public int bet = 0;

  // split hand properties
  public Hand splitHand;
  public int splitBet;

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
  public void hit(boolean isSplit, Card newCard) {
    if (isSplit) {
      splitHand.addCard(newCard);
    } else {
      hand.addCard(newCard);
    }
  }

  // puts an unfinished bet back into balance
  public void refundBet() {
    balance += bet;
    bet = 0;
  }

  // returns whether double was successful
  public boolean doubleBet(boolean isSplit, Card newCard) {
    if (isSplit) {
      // check if there's enough balance to double
      if (balance < splitBet) {
        return false;
      }

      // double bet and hit
      balance -= splitBet;
      splitBet *= 2;
      this.hit(true, newCard);

      return true;
    } else {
      if (balance < bet) {
        return false;
      }

      balance -= bet;
      bet *= 2;
      this.hit(false, newCard);

      return true;
    }
  }

  // returns whether split was successful
  public boolean splitBet(Card newCard1, Card newCard2) {
    // check if there's enough balance to split
    if (balance < bet) {
      return false;
    }

    // create split bet
    splitBet = bet;
    balance -= splitBet;

    // create split hand and add a new card to each hand
    splitHand = new Hand();
    splitHand.addCard(hand.cards.remove(1));
    this.hit(false, newCard1);
    this.hit(true, newCard2);

    return true;
  }

  // put the value in bet back to balance, and clear bet.
  // called when game ties
  public void receiveTiePayout(boolean isSplit) {
    if (isSplit) {
      assert splitBet > 0;

      balance += splitBet;
      splitBet = 0;
    } else {
      assert bet > 0;

      balance += bet;
      bet = 0;
    }
  }

  // put twice the value in bet back to balance, and clear bet.
  // called when the player wins
  public void receiveWinPayout(boolean isSplit) {
    if (isSplit) {
      assert splitBet > 0;

      balance += 2 * splitBet;
      splitBet = 0;
    } else {
      assert bet > 0;

      balance += 2 * bet;
      bet = 0;
    }
  }

  // put half the value in bet back to balance, and clear bet.
  // called when the player surrenders
  public void receiveHalfPayout(boolean isSplit) {
    if (isSplit) {
      assert splitBet > 0;

      balance += splitBet / 2;
      splitBet = 0;
    } else {
      assert bet > 0;

      balance += bet / 2;
      bet = 0;
    }
  }
}
