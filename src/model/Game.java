package model;

class Game {
  public static Deck deck = new Deck(true);
  public static Player player = new Player();
  public static Dealer dealer = new Dealer();

  // state properties
  public static boolean betPlaced = false;
  public static boolean roundOver = false;
  public static boolean doubled = false;

  // remember to tryShuffle on new round

  public void makeBet() {
    // can't place a bet of less than 50
    if (player.bet < 50) {
      System.out.println("Bet needs to be > 50.");
      return;
    }

    betPlaced = true;

    initialHand();
  }

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
  }

  public boolean choiceHit() {
    return false;
  }

  public boolean choiceDouble() {
    return false;
  }

  public boolean choiceStand() {
    return false;
  }

  public boolean choiceSurrender() {
    // TODO: Ask Ivan about chips
    return false;
  }

  public boolean choiceSplit() {
    // TODO: Next iterations
    return false;
  }
}
