package model;

import java.util.Collections;
import java.util.Stack;

class Game {
  public Deck deck = new Deck(true);
  public Player player = new Player();
  public Dealer dealer = new Dealer();

  // state properties
  private boolean betPlaced = false;

  public Game() {
    // a
  }

  public void makeBet() {
    // a
  }
}
