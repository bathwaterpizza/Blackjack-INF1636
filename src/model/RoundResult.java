package model;

// contains all the possible outcomes for a round, and their corresponding messages.
// each hand will have an associated result once the round is over
public enum RoundResult {
  WIN("You won!"),
  LOSS("You lost!"),
  TIE("It's a tie!"),
  WIN_BLACKJACK("You won! Blackjack!"),
  LOSS_BLACKJACK("You lost! Dealer Blackjack!"),
  TIE_BLACKJACK("It's a tie! Both Blackjacks!");

  private final String message;

  RoundResult(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return message;
  }
}
