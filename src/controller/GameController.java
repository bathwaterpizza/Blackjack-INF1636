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
    view.setBet(0);
  }

  // methods to update the view
  // NOTE: Observer
  public void notifyMoney() {
    view.setBalance(model.getBalance());
    // gets the total bet value, considering both hands
    view.setBet(model.getBet(true) + model.getBet(false));
  }

  // NOTE: Observer
  public void notifyHand() {
    view.setDealerCards(model.getDealerCards());
    view.setDealerPoints(model.getDealerPoints());
  }

  // methods to update the model,
  // and display error messages upon failure
  public void requestExit() {
    model.choiceExit();
  }

  public void requestDouble() {
    boolean success = model.choiceDouble();

    if (!success) {
      view.message("You can't double your bet now");
    }
  }

  public void requestSplit() {
    boolean success = model.choiceSplit();

    if (!success) {
      view.message("You can't split your hand now");
    }
  }

  public void requestClear() {
    boolean success = model.choiceClear();

    if (!success) {
      view.message("You can't clear your bet now");
    }
  }

  public void requestDeal() {
    boolean success = model.choiceDeal();

    if (!success) {
      view.message("You can't deal now");
    }
  }

  public void requestHit() {
    boolean success = model.choiceHit();

    if (!success) {
      view.message("You can't hit now");
    }
  }

  public void requestStand() {
    boolean success = model.choiceStand();

    if (!success) {
      view.message("You can't stand now");
    }
  }

  public void requestSurrender() {
    boolean success = model.choiceSurrender();

    if (!success) {
      view.message("You can't surrender now");
    }
  }

  public void requestRaiseBet(Chip chip) {
    boolean success = model.choiceIncBet(chip);

    if (!success) {
      view.message("You can't raise your bet now");
    }
  }

  public void requestLowerBet(Chip chip) {
    boolean success = model.choiceDecBet(chip);

    if (!success) {
      view.message("You can't lower your bet now");
    }
  }
}
