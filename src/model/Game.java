package model;

// static class that contains the game state and player objects
class Game {
  private static final int MIN_BET = 50;

  // global game properties
  public static Deck deck = new Deck(true);
  public static Player player = new Player();
  public static Dealer dealer = new Dealer();

  // main hand state properties
  public static boolean betPlaced = false;
  public static boolean roundOver = false;
  public static boolean won = false;
  public static boolean tied = false;
  public static boolean surrendered = false;

  // split hand state properties
  public static boolean split = false;
  public static boolean splitPlaying = false;
  public static boolean splitWon = false;
  public static boolean splitTied = false;
  public static boolean splitSurrendered = false;

  // internal function to deal the first hand for the player and the dealer
  private static void dealInitialHand(Card playerCard1, Card playerCard2, Card dealerCard1, Card dealerCard2) {
    // 2 cards for the player
    player.hand.addCard(playerCard1);
    player.hand.addCard(playerCard2);

    // 2 cards for the dealer
    dealer.hand.addCard(dealerCard1);
    dealer.hand.addCard(dealerCard2);

    // check if either the player or the dealer won on initial hand
    if (player.hand.isBlackjack() && dealer.hand.isBlackjack()) {
      // both blackjacks, tie
      tied = true;
      roundOver = true;

      payout();
    } else if (player.hand.isBlackjack()) {
      // player blackjack, player wins
      won = true;
      roundOver = true;

      payout();
    } else if (dealer.hand.isBlackjack()) {
      // dealer blackjack, player loses
      roundOver = true;

      payout();
    }
  }

  // called after the game is over to pay the player and clear his bet
  private static void payout() {
    assert roundOver;

    if (tied) {
      player.receiveTiePayout(false);
    } else if (won) {
      player.receiveWinPayout(false);
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

    // check if player can still play the game
    if (player.balance < MIN_BET) {
      System.out.println("No more money to play, exiting.");

      choiceExit();
    }
  }

  // plays for the dealer after all players' hands stand
  private static void playDealerHand() {
    assert !roundOver;

    // check if dealer should play
    if ((!split && player.hand.isBust()) || (player.hand.isBust() && player.splitHand.isBust())
        || (surrendered && splitSurrendered)) {
      // if the player busts on all available hands, or both hands surrendered,
      // it's an instant player loss and the dealer doesn't play

      won = false;
      if (split)
        splitWon = false;

      roundOver = true;

      return;
    } else {
      // dealer hits until 17
      while (dealer.hand.points < 17) {
        dealer.hit(Game.deck.getCard());
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
  }

  // called once main hand is done playing,
  // checks wheher to play the split hand or the dealer hand
  private static void playSplitHand() {
    if (split) {
      // main hand finished, split hand plays
      splitPlaying = true;
    } else {
      // no split hand, dealer plays
      playDealerHand();
      payout();
    }
  }

  // called when the player presses deal
  public static boolean choiceDeal() {
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

    dealInitialHand(deck.getCard(), deck.getCard(), deck.getCard(), deck.getCard());

    return true;
  }

  // overloaded version for testing with specific hands
  public static boolean choiceDeal(Card playerCard1, Card playerCard2, Card dealerCard1, Card dealerCard2) {
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
  public static boolean choiceClear() {
    if (betPlaced) {
      System.out.println("Can't start new round now.");
      return false;
    }

    // shuffle if >10% of deck is gone
    deck.tryReshuffle();

    // reset game
    betPlaced = false;
    roundOver = false;
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

    return true;
  }

  // called when the player presses hit,
  // returns whether it was successful
  public static boolean choiceHit() {
    if (splitPlaying) { // hit on split hand
      player.hit(true, Game.deck.getCard());

      // check if round is over
      if (player.splitHand.points == 21 || player.splitHand.isBust()) {
        // dealer plays
        playDealerHand();
        payout();
      }

      return true;
    } else { // hit on main hand
      // check if can hit
      if (roundOver || !betPlaced) {
        System.out.println("Can't hit now.");
        return false;
      }

      player.hit(false, Game.deck.getCard());

      // check if main hand play is over
      if (player.hand.points == 21 || player.hand.isBust()) {
        playSplitHand();
      }

      return true;
    }
  }

  // called when the player presses exit
  public static void choiceExit() {
    System.exit(0);
  }

  // called when the player presses double,
  // returns whether it was successful
  public static boolean choiceDouble() {
    if (splitPlaying) { // double on split hand
      if (player.splitHand.size() > 2) {
        System.out.println("Can't split double now.");
        return false;
      }

      Card newCard = Game.deck.getCard();
      boolean success = player.doubleBet(true, newCard);
      if (!success) {
        // cannot split double. return card to the top of the deck
        deck.putCard(newCard);
        System.out.println("No money to split double.");
        return false;
      }

      playDealerHand();
      payout();

      return true;
    } else { // double on main hand
      // check if can double
      if (roundOver || !betPlaced || player.hand.size() > 2) {
        System.out.println("Can't double now.");
        return false;
      }

      Card newCard = Game.deck.getCard();
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
  public static boolean choiceStand() {
    if (splitPlaying) {
      playDealerHand();
      payout();

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
  public static boolean choiceSurrender() {
    if (splitPlaying) {
      // check if can surrender
      if (player.hand.size() > 2) {
        System.out.println("Can't split surrender now.");
        return false;
      }

      splitSurrendered = true;
      playDealerHand();
      payout();

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
  public static boolean choiceSplit() {
    // check if can split
    if (roundOver || !betPlaced || player.hand.size() > 2 || split || !player.hand.isPair()) {
      System.out.println("Can't split now.");
      return false;
    }

    Card newCard1 = Game.deck.getCard();
    Card newCard2 = Game.deck.getCard();
    boolean success = player.splitBet(newCard1, newCard2);
    if (!success) {
      // cannot split. return cards to the top of the deck
      deck.putCard(newCard2);
      deck.putCard(newCard1);
      System.out.println("No money to split.");
      return false;
    }

    splitPlaying = false;
    split = true;

    return true;
  }
}
