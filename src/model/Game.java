package model;

class Game {
  public Deck deck = new Deck(true);
  public Player player = new Player();
  public Dealer dealer = new Dealer();

  // state properties
  public boolean betPlaced = false;

  public Game() {
    // a
  }

  public void makeBet() {
    // can't place a bet of zero
    if (player.bet <= 0) {
      System.out.println("You need to place a bet first.");
      return;
    }

    betPlaced = true;
  }
}
