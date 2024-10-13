package model;

class Game {
  public static Deck deck = new Deck(true);
  public static Player player = new Player();
  public static Dealer dealer = new Dealer();

  // state properties
  public static boolean betPlaced = false;
  public static boolean roundOver = false;

  public Game() {
    // a
  }

  public void makeBet() {
    // can't place a bet of less than 50
    if (player.bet < 50) {
      System.out.println("Bet needs to be > 50.");
      return;
    }

    betPlaced = true;

    // dealFirstCards
  }
}
