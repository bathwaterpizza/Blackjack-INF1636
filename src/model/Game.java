package model;

class Game {
  public static Deck deck = new Deck(true);
  public static Player player = new Player();
  public static Dealer dealer = new Dealer();

  // state properties
  public static boolean betPlaced = false;
  public static boolean roundOver = false;
  public static boolean won = false;
  public static boolean tied = false;
  public static boolean surrendered = false;

  // called when the player presses deal
  public static void makeBet() {
    if (betPlaced) {
      System.out.println("Bet already placed.");
      return;
    }

    // can't place a bet of less than 50
    if (player.bet < 50) {
      System.out.println("Bet needs to be >= 50.");
      return;
    }

    betPlaced = true;

    initialHand();
  }

  // internal function to deal the first hand for the player and the dealer
  private static void initialHand() {
    // 2 cards for the player
    player.currentHand.addCard(deck.getCard());
    player.currentHand.addCard(deck.getCard());

    // 2 cards for the dealer
    dealer.currentHand.addCard(deck.getCard());
    dealer.currentHand.addCard(deck.getCard());

    // check if either the player or the dealer won on initial hand
    if (player.currentHand.isBlackjack() && dealer.currentHand.isBlackjack()) {
      // both blackjacks, tie
      tied = true;
      roundOver = true;

      payout();
    } else if (player.currentHand.isBlackjack()) {
      // player blackjack, player wins
      won = true;
      roundOver = true;

      payout();
    } else if (dealer.currentHand.isBlackjack()) {
      // dealer blackjack, player loses
      roundOver = true;

      payout();
    }
  }

  // called after the game is over to pay the player and clear his bet
  private static void payout() {
    assert roundOver;

    if (tied) {
      player.receiveTiePayout();
    } else if (won) {
      player.receiveWinPayout();
    } else if (surrendered) {
      player.receiveHalfPayout();
    } else {
      player.bet = 0;
    }
  }

  // makes the dealer plays after the player stands.
  // calls handleRoundOver at the end
  private static void dealerPlay() {
    assert !roundOver;

    // dealer hits until 17
    while (dealer.currentHand.points < 17) {
      boolean success = dealer.hit(Game.deck.getCard());
      assert success;
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
    payout();
  }

  // resets state properties and reshuffles deck if needed.
  // called when player presses new round
  public static void choiceNewRound() {
    // state properties
    betPlaced = false;
    roundOver = false;
    won = false;
    tied = false;
    surrendered = false;

    // reset and shuffle if >10% of deck is gone
    deck.tryReshuffle();
    player.bet = 0;
    player.currentHand.clear();
    dealer.currentHand.clear();
  }

  // called when the player presses hit,
  // returns whether it was successful
  public static boolean choiceHit() {
    // check if can hit
    if (roundOver || !betPlaced) {
      System.out.println("Can't hit now.");
      return false;
    }

    boolean success = player.hit(Game.deck.getCard());

    assert success;

    // check game status
    if (player.currentHand.points == 21) {
      // dealer plays
      dealerPlay();
    } else if (player.currentHand.isBust()) {
      // player loses
      roundOver = true;
      payout();
    }

    return true;
  }

  // called when the player presses double,
  // returns whether it was successful
  public static boolean choiceDouble() {
    // check if can double
    if (roundOver || !betPlaced || player.currentHand.size() > 2) {
      System.out.println("Can't double now.");
      return false;
    }

    Card newCard = Game.deck.getCard();
    boolean success = player.doubleBet(newCard);
    if (!success) {
      // cannot double. return card to the top of the deck
      deck.putCard(newCard);
      System.out.println("No money to double.");
      return false;
    }

    if (player.currentHand.isBust()) {
      // bust, player loses
      roundOver = true;
      payout();
    } else {
      // player didn't bust, dealer plays
      dealerPlay();
    }

    return true;
  }

  // called when the player presses stand,
  // returns whether it was successful
  public static boolean choiceStand() {
    // check if can stand
    if (roundOver || !betPlaced) {
      System.out.println("Can't stand now.");
      return false;
    }

    dealerPlay();

    return true;
  }

  // called when the player presses surrender,
  // returns whether it was successful
  public static boolean choiceSurrender() {
    // check if can surrender
    if (roundOver || !betPlaced) {
      System.out.println("Can't surrender now.");
      return false;
    }

    roundOver = true;
    surrendered = true;
    payout();

    return true;
  }

  // called when the player presses split,
  // returns whether it was successful
  public static boolean choiceSplit() {
    // TODO: Next iterations
    return false;
  }
}
