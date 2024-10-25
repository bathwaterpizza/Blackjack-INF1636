package controller;

import view.*;
import model.*;

// called by Main to initialize the game,
// contains an API for the view to interact with the model
public class GameController {
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
}
