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

  public void addCard(Card c) {
    cards.add(c);
    updatePoints();
  }

  public boolean isBlackjack() {
    return points == 21 && cards.size() == 2;
  }

  public boolean isBust() {
    return points > 21;
  }

  // To decide if the hand can split
  public boolean isPair() {
    if (cards.size() == 2) {
      return cards.get(0).getValue() == cards.get(1).getValue();
    }
    return false;
  }

  public int getPoints() {
    return points;
  }
}
