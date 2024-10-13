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
  public static boolean tied = false;

  // called when the player presses deal
  public void makeBet() {
    assert betPlaced == false;

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
    player.currentHand = new Hand();
    dealer.currentHand = new Hand();

    // 2 cards for the player
    player.currentHand.addCard(deck.getCard());
    player.currentHand.addCard(deck.getCard());

    // 2 cards for the dealer
    dealer.currentHand.addCard(deck.getCard());
    dealer.currentHand.addCard(deck.getCard());

    // check if either the player or the dealer won on initial hand
    if (player.currentHand.isBlackjack() && dealer.currentHand.isBlackjack()) {
      // both blackjacks, tie
      won = false;
      tied = true;
      roundOver = true;

      handleRoundOver();
    } else if (player.currentHand.isBlackjack()) {
      // player blackjack, player wins
      won = true;
      tied = false;
      roundOver = true;

      handleRoundOver();
    } else if (dealer.currentHand.isBlackjack()) {
      // dealer blackjack, player loses
      won = false;
      tied = false;
      roundOver = true;

      handleRoundOver();
    }
  }

  // called when the player presses hit,
  // returns whether it was successful
  public boolean choiceHit() {
    // check if can hit
    if (roundOver || !betPlaced) {
      return false;
    }

    boolean success = player.hit();

    assert success;

    // check game status
    if (player.currentHand.points == 21) {
      // dealer plays
      dealerPlay();
    } else if (player.currentHand.isBust()) {
      // player loses
      roundOver = true;
      won = false;
      handleRoundOver();
    }

    return true;
  }

  // called after the game is over to pay the player, or flush his bet
  private void handleRoundOver() {
    if (!roundOver)
      return;

  }

  // makes the dealer plays after the player stands.
  // calls handleRoundOver at the end
  private void dealerPlay() {
    // dealer hits until 17

    assert !roundOver;

    while (dealer.currentHand.points < 17) {
      dealer.hit();
    }

    // dealer finished playing, check results
    // at this point, player hand <= 21
    if (dealer.currentHand.isBust()) {
      // dealer bust, player wins
      won = true;
    } else if (dealer.currentHand.points > player.currentHand.points) {
      // dealer wins
      won = false;
    } else if (dealer.currentHand.points == player.currentHand.points) {
      // round draw
      won = false;
      tied = true;
    } else {
      // player wins
      won = true;
    }

    roundOver = true;
    handleRoundOver();
  }

  public void choiceNewRound() {
    // TODO: Player clicked on new round, reset everything
    // dont forget to tryShuffle
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
