package model;

class Card {
  public Suit suit;
  public Rank rank;

  public Card(Suit suit, Rank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  // get the value of the card according to the blackjack rules
  public int getValue() {
    switch (rank) {
      case ACE:
        // Ace will always be 1, and the Hand class will decide whether it should be
        // considered 1 or 11 for the total hand value
        return 1;
      case TWO:
        return 2;
      case THREE:
        return 3;
      case FOUR:
        return 4;
      case FIVE:
        return 5;
      case SIX:
        return 6;
      case SEVEN:
        return 7;
      case EIGHT:
        return 8;
      case NINE:
        return 9;
      case TEN:
      case JACK:
      case QUEEN:
      case KING:
        return 10;
      default:
        System.out.println("Should not happen! unknown card.");
        return -1;
    }
  }

  // making sure that == works as expected
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Card card = (Card) obj;
    return suit == card.suit && rank == card.rank;
  }
}
