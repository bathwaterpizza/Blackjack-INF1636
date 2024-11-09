package model;

import java.util.ArrayList;

// represents a hand of cards in the game, from players or the dealer
class Hand {
  ArrayList<Card> cards = new ArrayList<>();
  int points = 0;

  // calculate the total value of the hand,
  // considering aces as 11 if the total is less than 21
  private void updatePoints() {
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

    points = sum;
  }

  // add a card and udpate the hand value
  void addCard(Card c) {
    cards.add(c);
    updatePoints();
  }

  // check if the hand is a blackjack
  boolean isBlackjack() {
    return points == 21 && cards.size() == 2;
  }

  // check if the hand is a bust (over 21 value)
  boolean isBust() {
    return points > 21;
  }

  // To decide if the hand can split
  boolean isPair() {
    if (cards.size() == 2) {
      return cards.get(0).getValue() == cards.get(1).getValue();
    }

    return false;
  }

  // empty the hand
  void clear() {
    cards.clear();
    points = 0;
  }

  // amount of cards in the hand
  int size() {
    return cards.size();
  }

  // tostring for debug
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (Card card : cards) {
      sb.append(card.toString()).append(", ");
    }

    if (sb.length() > 0) {
      sb.setLength(sb.length() - 2); // remove trailing comma and space
    }

    sb.append(" (" + points + ")");

    return sb.toString();
  }
}
