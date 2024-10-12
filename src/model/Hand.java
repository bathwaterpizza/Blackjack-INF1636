package model;

import java.util.ArrayList;

class Hand {
  public ArrayList<Card> cards = new ArrayList<>();
  public int points = 0;

  void updatePoints() {
    int sum = 0;
    boolean hasAce = false;
    for (Card c : cards) {
      if (c.rank == Rank.ACE) {
        hasAce = true;
      }
      sum += c.getValue();
    }

    if (hasAce && sum < 12) {
      sum += 10;
    }

    this.points = sum;
  }
}