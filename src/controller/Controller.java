package controller;

import view.*;

// called by Main to initialize the game,
// contains an API for the view to interact with the model
public class Controller {
  MenuFrame menu;

  public void initGameUI() {
    menu = new MenuFrame();
    menu.spawnMenu();
  }
}
