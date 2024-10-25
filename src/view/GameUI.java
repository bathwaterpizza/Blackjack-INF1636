package view;

// public class that contains the view API
public class GameUI {
  private static GameUI instance = null;

  // frame properties
  MenuFrame menu;
  DealerFrame dealer;

  // singleton pattern
  private GameUI() {
  }

  public static GameUI getAPI() {
    if (instance == null) {
      instance = new GameUI();
    }

    return instance;
  }

  // opens the menu window.
  // should only be called once per game instance
  public void openMenuWindow() {
    menu = new MenuFrame();
    menu.openWindow();
  }

  // closes the menu window.
  // should only be called once per game instance
  public void closeMenuWindow() {
    menu.closeWindow();
  }

  // opens the dealer window.
  // should only be called once per game instance
  public void openDealerWindow() {
    dealer = new DealerFrame();
    dealer.openWindow();
  }
}
