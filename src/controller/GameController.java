package controller;

import view.*;
import model.*;

// called by Main to initialize the game,
// contains an API for the view to interact with the model
public class GameController {
  // singleton instance
  private static GameController instance = null;

  // singleton pattern
  private GameController() {
    // register the view as an observer of the model
    Game.getAPI().addObserver(GameUI.getAPI());

    // initialize game UI
    GameUI.getAPI().openMenuWindow();
  }

  public static GameController getAPI() {
    if (instance == null) {
      instance = new GameController();
    }

    return instance;
  }

  // called by the view when the player clicks on new game in menu
  // close menu, open dealer window and start game
  public void startGame(boolean isLoad) {
    GameUI.getAPI().closeMenuWindow();
    GameUI.getAPI().openDealerWindow();

    if (!isLoad)
      Game.getAPI().choiceClear();
  }

  // methods to update the model, representing a game choice from the player.
  // these will display an error message upon failure.
  public void requestExit() {
    Game.getAPI().choiceExit();
  }

  public void requestDouble() {
    boolean success = Game.getAPI().choiceDouble();

    if (!success) {
      GameUI.getAPI().messageGame("You can't double your bet now");
      return;
    }
  }

  public void requestSplit() {
    boolean success = Game.getAPI().choiceSplit();

    if (!success) {
      GameUI.getAPI().messageGame("You can't split your hand now");
      return;
    }
  }

  public void requestClear() {
    boolean success = Game.getAPI().choiceClear();

    if (!success) {
      GameUI.getAPI().messageGame("You can't clear your bet now");
      return;
    }
  }

  public void requestDeal() {
    boolean success = Game.getAPI().choiceDeal();

    if (!success) {
      GameUI.getAPI().messageGame("You can't deal now");
      return;
    }
  }

  public void requestHit() {
    boolean success = Game.getAPI().choiceHit();

    if (!success) {
      GameUI.getAPI().messageGame("You can't hit now");
      return;
    }
  }

  public void requestStand() {
    boolean success = Game.getAPI().choiceStand();

    if (!success) {
      GameUI.getAPI().messageGame("You can't stand now");
      return;
    }
  }

  public void requestSurrender() {
    boolean success = Game.getAPI().choiceSurrender();

    if (!success) {
      GameUI.getAPI().messageGame("You can't surrender now");
      return;
    }
  }

  public void requestRaiseBet(Chip chip) {
    boolean success = Game.getAPI().choiceIncBet(chip);

    if (!success) {
      GameUI.getAPI().messageGame("You can't raise your bet now");
      return;
    }
  }

  public void requestLowerBet(Chip chip) {
    boolean success = Game.getAPI().choiceDecBet(chip);

    if (!success) {
      GameUI.getAPI().messageGame("You can't lower your bet now");
      return;
    }
  }

  // save/load methods
  public void requestSave(String filePath) {
    Game.saveGame(filePath);
  }

  public void requestLoad(String filePath) {
    Game.loadGame(filePath);
    
    // add the view as an observer of the loaded model
    Game.getAPI().addObserver(GameUI.getAPI());

    // force model to update its observers
    Game.getAPI().forceNotifyAll();
  }
}
