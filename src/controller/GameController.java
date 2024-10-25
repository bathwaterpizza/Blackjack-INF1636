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
    view.setBet(model.getBet());
  }

  public void requestExit() {
    model.choiceExit();
  }

  public void requestDouble() {
    model.choiceDouble();
  }

  public void requestSplit() {
    model.choiceSplit();
  }

  public void requestClear() {
    model.choiceClear();
  }

  public void requestDeal() {
    model.choiceDeal();
  }

  public void requestHit() {
    model.choiceHit();
  }

  public void requestStand() {
    model.choiceStand();
  }

  public void requestSurrender() {
    model.choiceSurrender();
  }
}
