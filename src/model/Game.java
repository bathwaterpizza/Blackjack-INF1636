package model;

class Game {
  public static Deck deck = new Deck(true);
  public static Player player = new Player();
  public static Dealer dealer = new Dealer();

  // state properties
  public static boolean betPlaced = false;
  public static boolean roundOver = false;
  public static boolean doubled = false;
  public static boolean won = false;

  // remember to tryShuffle on new round

  // called when the player presses deal
  public void makeBet() {
    // can't place a bet of less than 50
    if (player.bet < 50) {
      System.out.println("Bet needs to be > 50.");
      return;
    }

    betPlaced = true;

    initialHand();
  }

  // internal function to deal the first hand for the player and the dealer
  private void initialHand() {
    // deal initial two cards for player and dealer
    player.currentHand = new Hand();
    dealer.currentHand = new Hand();

    // 2 cards for the player
    player.currentHand.addCard(deck.getCard());
    player.currentHand.addCard(deck.getCard());

    // 2 cards for the dealer
    dealer.currentHand.addCard(deck.getCard());
    dealer.currentHand.addCard(deck.getCard());

    // TODO: check if either hand isBlackjack()
  }

  // called when the player presses hit,
  // returns whether it was successful
  public boolean choiceHit() {
    // check if can hit
    if (roundOver) {
      return false;
    }

    boolean success = player.hit();

    if (!success)
      return false;

    // check game status
    if (player.currentHand.points == 21) {
      // win

    } else if (player.currentHand.isBust()) {
      // lose
    }

    return true;
  }

  // called when the player presses double,
  // returns whether it was successful
  public boolean choiceDouble() {
    // check if can double, other than the player checks
    return false;
  }

  // called when the player presses stand,
  // returns whether it was successful
  public boolean choiceStand() {
    // dealer does what it needs to, then game ends
    return false;
  }

  // called when the player presses surrender,
  // returns whether it was successful
  public boolean choiceSurrender() {
    // TODO: Ask Ivan about chips
    return false;
  }

  // called when the player presses split,
  // returns whether it was successful
  public boolean choiceSplit() {
    // TODO: Next iterations
    return false;
  }
}
