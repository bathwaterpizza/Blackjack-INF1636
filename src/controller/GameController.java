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
    // register the view as an observer of the model
    model.addObserver(view);

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
  }

  public void requestClear() {
    boolean success = model.choiceClear();

    if (!success) {
      view.messageGame("You can't clear your bet now");
      return;
    }
  }

  public void requestDeal() {
    boolean success = model.choiceDeal();

    if (!success) {
      view.messageGame("You can't deal now");
      return;
    }
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
