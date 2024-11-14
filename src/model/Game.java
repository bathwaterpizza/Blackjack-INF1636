package model;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

import observer.*;

// public class that contains the model API
public class Game implements IGameObservable, Serializable {
  // fixed ID for serialization
  private static final long serialVersionUID = 1L;
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
  RoundResult result = null;

  // split hand state properties
  boolean split = false;
  boolean splitPlaying = false;
  boolean splitWon = false;
  boolean splitTied = false;
  boolean splitSurrendered = false;
  RoundResult splitResult = null;

  // observer list, not serializable
  private transient List<IGameObserver> observers = new ArrayList<IGameObserver>();

  // singleton pattern
  private Game() {
  }

  public static Game getAPI() {
    if (instance == null) {
      instance = new Game();
    }

    return instance;
  }

  // observable interface methods
  public void addObserver(IGameObserver observer) {
    observers.add(observer);
  }

  public void remObserver(IGameObserver observer) {
    observers.remove(observer);
  }

  public void notifyHandUpdate() {
    GameState state = new GameState(
        getDealerCards(),
        getDealerPoints(),
        getPlayerCards(false),
        getPlayerCards(true),
        getPlayerPoints(false),
        getPlayerPoints(true),
        getSplitPlaying(),
        getBalance(),
        getBet(false) + getBet(true),
        getBet(false),
        getBet(true));

    for (IGameObserver observer : observers) {
      observer.updateHand(this, state);
    }
  }

  public void notifyMoneyUpdate() {
    GameState state = new GameState(
        getDealerCards(),
        getDealerPoints(),
        getPlayerCards(false),
        getPlayerCards(true),
        getPlayerPoints(false),
        getPlayerPoints(true),
        getSplitPlaying(),
        getBalance(),
        getBet(false) + getBet(true),
        getBet(false),
        getBet(true));

    for (IGameObserver observer : observers) {
      observer.updateMoney(this, state);
    }
  }

  public void notifyRoundOver() {
    for (IGameObserver observer : observers) {
      observer.updateRoundResult(this, result, splitResult);
    }
  }

  public void notifyWindowUpdate() {
    for (IGameObserver observer : observers) {
      // player main hand window should display as long as the bet is placed,
      // note that the bet is still placed when the round is over but hasn't been
      // cleared yet.

      // player split hand window should display as long as there is a split
      observer.updateWindows(this, betPlaced, split);
    }
  }

  // internal function to deal the first hand for the player and the dealer
  private void dealInitialHand(Card playerCard1, Card playerCard2, Card dealerCard1, Card dealerCard2) {
    // 2 cards for the player
    player.hand.addCard(playerCard1);
    player.hand.addCard(playerCard2);

    // 2 cards for the dealer
    dealer.hand.addCard(dealerCard1);
    dealer.hand.addCard(dealerCard2);

    notifyHandUpdate();

    // check if either the player or the dealer won on initial hand
    if (player.hand.isBlackjack() && dealer.hand.isBlackjack()) {
      // both blackjacks, tie
      won = false;
      tied = true;
      roundOver = true;
      result = RoundResult.TIE_BLACKJACK;

      payout();
      notifyRoundOver();
    } else if (player.hand.isBlackjack()) {
      // player blackjack, player wins
      won = true;
      tied = false;
      roundOver = true;
      result = RoundResult.WIN_BLACKJACK;

      payout();
      notifyRoundOver();
    } else if (dealer.hand.isBlackjack()) {
      // dealer blackjack, player loses
      won = false;
      tied = false;
      roundOver = true;
      result = RoundResult.LOSS_BLACKJACK;

      payout();
      notifyRoundOver();
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

    notifyMoneyUpdate();
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
      result = RoundResult.LOSS;
      if (split) {
        splitWon = false;
        splitResult = RoundResult.LOSS;
      }

      roundOver = true;
      payout();

      // update UI
      notifyHandUpdate();
      notifyRoundOver();

      return;
    } else {
      // dealer hits until 17
      while (dealer.hand.points < 17) {
        dealer.hit(deck.getCard());
      }

      // update UI
      notifyHandUpdate();
    }

    // dealer versus main hand
    if (!surrendered) {
      if (player.hand.isBust()) {
        // player bust, dealer wins
        won = false;
        result = RoundResult.LOSS;
      } else if (dealer.hand.isBust()) {
        // dealer bust, player wins
        won = true;
        result = RoundResult.WIN;
      } else if (dealer.hand.points > player.hand.points) {
        // dealer has more points, dealer wins
        won = false;
        result = RoundResult.LOSS;
      } else if (dealer.hand.points == player.hand.points) {
        // player and dealer have the same points, tie
        won = false;
        tied = true;
        result = RoundResult.TIE;
      } else {
        // player has more points, player wins
        won = true;
        result = RoundResult.WIN;
      }
    }

    // dealer versus split hand
    if (split && !splitSurrendered) {
      // check split hand results
      if (player.splitHand.isBust()) {
        // player bust, dealer wins
        splitWon = false;
        splitResult = RoundResult.LOSS;
      } else if (dealer.hand.isBust()) {
        // dealer bust, player wins
        splitWon = true;
        splitResult = RoundResult.WIN;
      } else if (dealer.hand.points > player.splitHand.points) {
        // dealer has more points, dealer wins
        splitWon = false;
        splitResult = RoundResult.LOSS;
      } else if (dealer.hand.points == player.splitHand.points) {
        // player and dealer have the same points, tie
        splitWon = false;
        splitTied = true;
        splitResult = RoundResult.TIE;
      } else {
        // player has more points, player wins
        splitWon = true;
        splitResult = RoundResult.WIN;
      }
    }

    roundOver = true;
    payout();

    // update UI
    notifyRoundOver();
  }

  // called once main hand is done playing,
  // checks whether to play the split hand or the dealer hand
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

    // open player main hand window
    notifyWindowUpdate();
    notifyMoneyUpdate();

    dealInitialHand(deck.getCard(), deck.getCard(), deck.getCard(), deck.getCard());

    // for testing
    // Card playerTestCard1 = new Card(Suit.SPADES, Rank.QUEEN);
    // Card playerTestCard2 = new Card(Suit.HEARTS, Rank.TEN);
    // Card dealerTestCard1 = new Card(Suit.CLUBS, Rank.FIVE);
    // Card dealerTestCard2 = new Card(Suit.HEARTS, Rank.SIX);
    // dealInitialHand(playerTestCard1, playerTestCard2, dealerTestCard1,
    //     dealerTestCard2);

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

    // open player main hand window
    notifyWindowUpdate();

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
    result = null;

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
      splitResult = null;

      player.splitBet = 0;
      player.splitHand.clear();
    }

    dealer.hand.clear();

    // check if player can still play the game
    if (player.balance < MIN_BET) {
      System.out.println("No more money to play, exiting.");

      System.exit(0);
    }

    // update UI
    notifyWindowUpdate();
    notifyHandUpdate();
    notifyMoneyUpdate();

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

      notifyHandUpdate();

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

      // update UI
      notifyHandUpdate();

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

      // update UI
      notifyHandUpdate();
      notifyMoneyUpdate();

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

      notifyHandUpdate();
      notifyMoneyUpdate();

      return true;
    }
  }

  // called when the player presses stand,
  // returns whether it was successful
  public boolean choiceStand() {
    if (splitPlaying) {
      playDealerHand();

      notifyHandUpdate();

      return true;
    } else {
      // check if can stand
      if (roundOver || !betPlaced) {
        System.out.println("Can't stand now.");
        return false;
      }

      playSplitHand();

      notifyHandUpdate();

      return true;
    }
  }

  // Called when the player presses surrender,
  // returns whether it was successful.
  // Note that if there is a split bet, then the half bet will only be refunded
  // after both hands finish playing.
  public boolean choiceSurrender() {
    if (splitPlaying) {
      // FIXED: rules do not allow surrendering after a split
      return false;

      /*
       * // check if can surrender
       * if (player.splitHand.size() > 2) {
       * System.out.println("Can't split surrender now.");
       * return false;
       * }
       * 
       * splitSurrendered = true;
       * playDealerHand();
       * 
       * notifyHandUpdate();
       * 
       * return true;
       */
    } else {
      // check if can surrender
      // FIXED: rules do not allow surrendering after a split
      if (roundOver || !betPlaced || player.hand.size() > 2 || split) {
        System.out.println("Can't surrender now.");
        return false;
      }

      surrendered = true;
      playSplitHand();

      notifyHandUpdate();

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

    // open player split hand window
    notifyWindowUpdate();

    // when splitting from aces, only one card can be drawn
    if (player.hand.cards.get(0).rank == Rank.ACE) {
      playDealerHand();
    }

    notifyHandUpdate();
    notifyMoneyUpdate();

    return true;
  }

  // called when the player clicks on a chip to increment the bet
  public boolean choiceIncBet(Chip chip) {
    if (betPlaced) {
      System.out.println("Bet already placed.");
      return false;
    }

    boolean success = player.incrementBet(chip);
    if (success) {
      notifyMoneyUpdate();
    }

    return success;
  }

  // called when the player clicks on a chip to decrement the bet
  public boolean choiceDecBet(Chip chip) {
    if (betPlaced) {
      System.out.println("Bet already placed.");
      return false;
    }

    boolean success = player.decrementBet(chip);
    if (success) {
      notifyMoneyUpdate();
    }

    return success;
  }

  // returns whether the split hand is playing or not
  private boolean getSplitPlaying() {
    return splitPlaying;
  }

  // returns the player's balance
  private double getBalance() {
    return player.balance;
  }

  // returns the player's current bet
  private int getBet(boolean isSplit) {
    return isSplit ? player.splitBet : player.bet;
  }

  private int getDealerPoints() {
    return dealer.hand.points;
  }

  private int getPlayerPoints(boolean isSplit) {
    return isSplit ? player.splitHand.points : player.hand.points;
  }

  private List<Integer> getDealerCards() {
    var list = new ArrayList<Integer>();

    for (Card card : dealer.hand.cards) {
      list.add(card.toInt());
    }

    return list;
  }

  private List<Integer> getPlayerCards(boolean isSplit) {
    var list = new ArrayList<Integer>();

    if (isSplit) {
      for (Card card : player.splitHand.cards) {
        list.add(card.toInt());
      }
    } else {
      for (Card card : player.hand.cards) {
        list.add(card.toInt());
      }
    }

    return list;
  }

  // save game to a file
  public static void saveGame(String filePath) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
      out.writeObject(instance);
    } catch (IOException e) {
      System.out.println("Error: could not save game");
      System.exit(1);
    }
  }

  // load game from a file
  public static void loadGame(String filePath) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
      instance = (Game) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Error: could not load game");
      System.exit(1);
    }
  }
}
