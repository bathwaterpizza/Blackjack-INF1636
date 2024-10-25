package model;

// public class that contains the model API
public class Game {
  // minimum bet to play a hand
  private static final int MIN_BET = 50;
  // singleton instance
  private static Game instance = null;

  // composition properties
  Deck deck = new Deck(true);
  Player player = new Player();
  Dealer dealer = new Dealer();

  // main hand state properties
  boolean betPlaced = false;
  boolean roundOver = true;
  boolean won = false;
  boolean tied = false;
  boolean surrendered = false;

  // split hand state properties
  boolean split = false;
  boolean splitPlaying = false;
  boolean splitWon = false;
  boolean splitTied = false;
  boolean splitSurrendered = false;

  // singleton pattern
  private Game() {
  }

  public static Game getAPI() {
    if (instance == null) {
      instance = new Game();
    }

    return instance;
  }

  // internal function to deal the first hand for the player and the dealer
  private void dealInitialHand(Card playerCard1, Card playerCard2, Card dealerCard1, Card dealerCard2) {
    // 2 cards for the player
    player.hand.addCard(playerCard1);
    player.hand.addCard(playerCard2);

    // 2 cards for the dealer
    dealer.hand.addCard(dealerCard1);
    dealer.hand.addCard(dealerCard2);

    // check if either the player or the dealer won on initial hand
    if (player.hand.isBlackjack() && dealer.hand.isBlackjack()) {
      // both blackjacks, tie
      won = false;
      tied = true;
      roundOver = true;

      payout();
    } else if (player.hand.isBlackjack()) {
      // player blackjack, player wins
      won = true;
      tied = false;
      roundOver = true;

      payout();
    } else if (dealer.hand.isBlackjack()) {
      // dealer blackjack, player loses
      won = false;
      tied = false;
      roundOver = true;

      payout();
    }
  }

  // called after the game is over to pay the player and clear his bet
  private void payout() {
    assert roundOver;

    if (tied) {
      player.receiveTiePayout(false);
    } else if (won) {
      // check for blackjack win, pays 3:2
      if (player.hand.isBlackjack() && !split) {
        player.receiveBlackjackPayout();
      } else {
        player.receiveWinPayout(false);
      }
    } else if (surrendered) {
      player.receiveHalfPayout(false);
    } else {
      // main hand lost
      player.bet = 0;
    }

    if (split) {
      if (splitTied) {
        player.receiveTiePayout(true);
      } else if (splitWon) {
        player.receiveWinPayout(true);
      } else if (splitSurrendered) {
        player.receiveHalfPayout(true);
      } else {
        // split hand lost
        player.splitBet = 0;
      }
    }
  }

  // plays for the dealer after all players' hands stand
  private void playDealerHand() {
    assert !roundOver;

    // check if dealer should play
    if ((!split && player.hand.isBust()) || (player.hand.isBust() && player.splitHand.isBust())
        || (!split && surrendered) || (surrendered && splitSurrendered)) {
      // if the player busts or surrenders on all available hands,
      // it's an instant player loss and the dealer doesn't play

      won = false;
      if (split)
        splitWon = false;

      roundOver = true;
      payout();

      return;
    } else {
      // dealer hits until 17
      while (dealer.hand.points < 17) {
        dealer.hit(instance.deck.getCard());
      }
    }

    // dealer versus main hand
    if (!surrendered) {
      if (player.hand.isBust()) {
        // player bust, dealer wins
        won = false;
      } else if (dealer.hand.isBust()) {
        // dealer bust, player wins
        won = true;
      } else if (dealer.hand.points > player.hand.points) {
        // dealer has more points, dealer wins
        won = false;
      } else if (dealer.hand.points == player.hand.points) {
        // player and dealer have the same points, tie
        won = false;
        tied = true;
      } else {
        // player has more points, player wins
        won = true;
      }
    }

    // dealer versus split hand
    if (split && !splitSurrendered) {
      // check split hand results
      if (player.splitHand.isBust()) {
        // player bust, dealer wins
        splitWon = false;
      } else if (dealer.hand.isBust()) {
        // dealer bust, player wins
        splitWon = true;
      } else if (dealer.hand.points > player.splitHand.points) {
        // dealer has more points, dealer wins
        splitWon = false;
      } else if (dealer.hand.points == player.splitHand.points) {
        // player and dealer have the same points, tie
        splitWon = false;
        splitTied = true;
      } else {
        // player has more points, player wins
        splitWon = true;
      }
    }

    roundOver = true;
    payout();
  }

  // called once main hand is done playing,
  // checks wheher to play the split hand or the dealer hand
  private void playSplitHand() {
    if (split) {
      // main hand finished, split hand plays
      splitPlaying = true;
    } else {
      // no split hand, dealer plays
      playDealerHand();
    }
  }

  // called when the player presses deal
  public boolean choiceDeal() {
    if (betPlaced) {
      System.out.println("Bet already placed.");
      return false;
    }

    // can't place a bet of less than MIN_BET
    if (player.bet < MIN_BET) {
      System.out.println("Bet needs to be at least " + MIN_BET + ".");
      return false;
    }

    betPlaced = true;
    roundOver = false;

    dealInitialHand(deck.getCard(), deck.getCard(), deck.getCard(), deck.getCard());

    return true;
  }

  // overloaded version for testing with specific hands
  public boolean choiceDeal(Card playerCard1, Card playerCard2, Card dealerCard1, Card dealerCard2) {
    if (betPlaced) {
      System.out.println("Bet already placed.");
      return false;
    }

    // can't place a bet of less than MIN_BET
    if (player.bet < MIN_BET) {
      System.out.println("Bet needs to be at least " + MIN_BET + ".");
      return false;
    }

    betPlaced = true;

    assert playerCard1 != null;
    assert playerCard2 != null;
    assert dealerCard1 != null;
    assert dealerCard2 != null;

    dealInitialHand(playerCard1, playerCard2, dealerCard1, dealerCard2);

    return true;
  }

  // resets state properties and reshuffles deck if needed.
  // called when player presses new round
  public boolean choiceClear() {
    if (!roundOver) {
      System.out.println("Can't start new round now.");
      return false;
    }

    // shuffle if >10% of deck is gone
    deck.tryReshuffle();

    // reset game
    betPlaced = false;
    roundOver = true;
    won = false;
    tied = false;
    surrendered = false;

    if (player.bet > 0) {
      // refund unfinished bet to balance
      player.refundBet();
    }

    player.bet = 0;
    player.hand.clear();

    if (split) {
      split = false;
      splitPlaying = false;
      splitWon = false;
      splitTied = false;
      splitSurrendered = false;

      player.splitBet = 0;
      player.splitHand.clear();
    }

    dealer.hand.clear();

    // check if player can still play the game
    if (player.balance < MIN_BET) {
      System.out.println("No more money to play, exiting.");

      System.exit(0);
    }

    return true;
  }

  // called when the player presses hit,
  // returns whether it was successful
  public boolean choiceHit() {
    if (splitPlaying) { // hit on split hand
      player.hit(true, deck.getCard());

      // check if round is over
      if (player.splitHand.isBust()) {
        // dealer plays
        playDealerHand();
      }

      return true;
    } else { // hit on main hand
      // check if can hit
      if (roundOver || !betPlaced) {
        System.out.println("Can't hit now.");
        return false;
      }

      player.hit(false, deck.getCard());

      // check if main hand play is over
      if (player.hand.isBust()) {
        playSplitHand();
      }

      return true;
    }
  }

  // called when the player presses exit
  public void choiceExit() {
    System.exit(0);
  }

  // called when the player presses double,
  // returns whether it was successful
  public boolean choiceDouble() {
    if (splitPlaying) { // double on split hand
      if (player.splitHand.size() > 2) {
        System.out.println("Can't split double now.");
        return false;
      }

      Card newCard = deck.getCard();
      boolean success = player.doubleBet(true, newCard);
      if (!success) {
        // cannot split double. return card to the top of the deck
        deck.putCard(newCard);
        System.out.println("No money to split double.");
        return false;
      }

      playDealerHand();

      return true;
    } else { // double on main hand
      // check if can double
      if (roundOver || !betPlaced || player.hand.size() > 2) {
        System.out.println("Can't double now.");
        return false;
      }

      Card newCard = deck.getCard();
      boolean success = player.doubleBet(false, newCard);
      if (!success) {
        // cannot double. return card to the top of the deck
        deck.putCard(newCard);
        System.out.println("No money to double.");
        return false;
      }

      playSplitHand();

      return true;
    }
  }

  // called when the player presses stand,
  // returns whether it was successful
  public boolean choiceStand() {
    if (splitPlaying) {
      playDealerHand();

      return true;
    } else {
      // check if can stand
      if (roundOver || !betPlaced) {
        System.out.println("Can't stand now.");
        return false;
      }

      playSplitHand();

      return true;
    }
  }

  // called when the player presses surrender,
  // returns whether it was successful
  public boolean choiceSurrender() {
    if (splitPlaying) {
      // check if can surrender
      if (player.splitHand.size() > 2) {
        System.out.println("Can't split surrender now.");
        return false;
      }

      splitSurrendered = true;
      playDealerHand();

      return true;
    } else {
      // check if can surrender
      if (roundOver || !betPlaced || player.hand.size() > 2) {
        System.out.println("Can't surrender now.");
        return false;
      }

      surrendered = true;
      playSplitHand();

      return true;
    }
  }

  // called when the player presses split,
  // returns whether it was successful
  public boolean choiceSplit() {
    // check if can split
    if (roundOver || !betPlaced || player.hand.size() > 2 || split || !player.hand.isPair()) {
      System.out.println("Can't split now.");
      return false;
    }

    Card newCard1 = deck.getCard();
    Card newCard2 = deck.getCard();
    boolean success = player.splitBet(newCard1, newCard2);
    if (!success) {
      // cannot split. return cards to the top of the deck
      deck.putCard(newCard2);
      deck.putCard(newCard1);
      System.out.println("No money to split.");
      return false;
    }

    split = true;

    // when splitting from aces, only one card can be drawn
    if (player.hand.cards.get(0).rank == Rank.ACE) {
      playDealerHand();
    }

    return true;
  }

  // called when the player clicks on a chip to increment the bet
  public boolean choiceIncBet(Chip chip) {
    if (betPlaced) {
      System.out.println("Bet already placed.");
      return false;
    }

    return player.incrementBet(chip);
  }

  // called when the player clicks on a chip to decrement the bet
  public boolean choiceDecBet(Chip chip) {
    if (betPlaced) {
      System.out.println("Bet already placed.");
      return false;
    }

    return player.decrementBet(chip);
  }
}
