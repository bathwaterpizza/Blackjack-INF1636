package controller;

import view.*;
import model.*;

// called by Main to initialize the game,
// contains an API for the view to interact with the model
public class GameController {
  // singleton instance
  private static GameController instance = null;

  // initialize model and view
  Game model = Game.getAPI();
  GameUI view = GameUI.getAPI();

  // singleton pattern
  private GameController() {
    // initialize game
    view.openMenuWindow();
  }

  public static GameController getAPI() {
    if (instance == null) {
      instance = new GameController();
    }

    return instance;
  }

  // called by the view when the player clicks on new game in menu
  // close menu, open dealer window and start game
  public void newGame() {
    view.closeMenuWindow();
    view.openDealerWindow();
    model.choiceClear();

    // initial value for the labels
    view.setBalance(model.getBalance());
    view.setTotalBet(0);
  }

  // methods to update the view
  // NOTE: Observer
  public void notifyMoneyUpdate() {
    // dealer window
    view.setBalance(model.getBalance());
    view.setTotalBet(model.getBet(true) + model.getBet(false));

    // player windows
    view.setHandBet(false, model.getBet(false));
    // split
    view.setHandBet(true, model.getBet(true));
  }

  // NOTE: Observer
  public void notifyHandUpdate() {
    // dealer window
    view.setDealerCards(model.getDealerCards());
    view.setDealerPoints(model.getDealerPoints());

    // player windows
    view.setPlayerCards(false, model.getPlayerCards(false));
    view.setPlayerPoints(false, model.getPlayerPoints(false));
    // split
    view.setPlayerCards(true, model.getPlayerCards(true));
    view.setPlayerPoints(true, model.getPlayerPoints(true));
  }

  // NOTE: Observer
  public void notifyRoundOver(boolean isSplit, String message) {
    view.messageHand(isSplit, message);
  }

  // methods to update the model,
  // and display error messages upon failure
  public void requestExit() {
    model.choiceExit();
  }

  public void requestDouble() {
    boolean success = model.choiceDouble();

    if (!success) {
      view.messageGame("You can't double your bet now");
      return;
    }
  }

  public void requestSplit() {
    boolean success = model.choiceSplit();

    if (!success) {
      view.messageGame("You can't split your hand now");
      return;
    }

    // NOTE: Observer
    view.openPlayerWindow(true);
    notifyMoneyUpdate();
    notifyHandUpdate();
  }

  public void requestClear() {
    boolean success = model.choiceClear();

    if (!success) {
      view.messageGame("You can't clear your bet now");
      return;
    }

    // NOTE: Observer
    view.closePlayerWindow(false);
    view.closePlayerWindow(true);
  }

  public void requestDeal() {
    boolean success = model.choiceDeal();

    if (!success) {
      view.messageGame("You can't deal now");
      return;
    }

    // NOTE: Observer
    view.openPlayerWindow(false);
    notifyMoneyUpdate();
    notifyHandUpdate();

    // check if there was a blackjack to display dialog accordingly
    model.checkBlackjacks();
  }

  public void requestHit() {
    boolean success = model.choiceHit();

    if (!success) {
      view.messageGame("You can't hit now");
      return;
    }
  }

  public void requestStand() {
    boolean success = model.choiceStand();

    if (!success) {
      view.messageGame("You can't stand now");
      return;
    }
  }

  public void requestSurrender() {
    boolean success = model.choiceSurrender();

    if (!success) {
      view.messageGame("You can't surrender now");
      return;
    }
  }

  public void requestRaiseBet(Chip chip) {
    boolean success = model.choiceIncBet(chip);

    if (!success) {
      view.messageGame("You can't raise your bet now");
      return;
    }
  }

  public void requestLowerBet(Chip chip) {
    boolean success = model.choiceDecBet(chip);

    if (!success) {
      view.messageGame("You can't lower your bet now");
      return;
    }
  }
}
