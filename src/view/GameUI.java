package view;

import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import model.Chip;

// public class that contains the view API
public class GameUI {
  // singleton instance
  private static GameUI instance = null;

  // asset properties
  private static final String ASSETS_PATH = "src/view/assets/";
  HashMap<Integer, Image> cardAssets = new HashMap<Integer, Image>();
  HashMap<Chip, Image> chipAssets = new HashMap<Chip, Image>();
  Image tableAsset;

  // UI properties
  public MenuFrame menuFrame = null;
  public DealerFrame dealerFrame = null;
  public PlayerFrame playerFrame = null;
  public PlayerFrame splitPlayerFrame = null;

  // singleton pattern
  private GameUI() {
    // load assets on game initialization
    loadAssets();
  }

  public static GameUI getAPI() {
    if (instance == null) {
      instance = new GameUI();
    }

    return instance;
  }

  // loads all the game images into memory
  private void loadAssets() {
    try {
      // table asset
      tableAsset = ImageIO.read(new File(ASSETS_PATH + "table.png"));

      // chip assets
      chipAssets.put(Chip.BLACK, ImageIO.read(new File(ASSETS_PATH + "chip1.png")));
      chipAssets.put(Chip.PINK, ImageIO.read(new File(ASSETS_PATH + "chip5.png")));
      chipAssets.put(Chip.BLUE, ImageIO.read(new File(ASSETS_PATH + "chip10.png")));
      chipAssets.put(Chip.RED, ImageIO.read(new File(ASSETS_PATH + "chip20.png")));
      chipAssets.put(Chip.GREEN, ImageIO.read(new File(ASSETS_PATH + "chip50.png")));
      chipAssets.put(Chip.GOLD, ImageIO.read(new File(ASSETS_PATH + "chip100.png")));

      // card assets,
      // these int values match the toInt and fromInt methods of the Card class

      // clubs
      cardAssets.put(0, ImageIO.read(new File(ASSETS_PATH + "ac.gif")));
      for (int i = 2; i <= 10; i++) {
        cardAssets.put(i - 1, ImageIO.read(new File(ASSETS_PATH + i + "c.gif")));
      }
      cardAssets.put(10, ImageIO.read(new File(ASSETS_PATH + "jc.gif")));
      cardAssets.put(11, ImageIO.read(new File(ASSETS_PATH + "qc.gif")));
      cardAssets.put(12, ImageIO.read(new File(ASSETS_PATH + "kc.gif")));

      // diamonds
      cardAssets.put(13, ImageIO.read(new File(ASSETS_PATH + "ad.gif")));
      for (int i = 2; i <= 10; i++) {
        cardAssets.put(i + 12, ImageIO.read(new File(ASSETS_PATH + i + "d.gif")));
      }
      cardAssets.put(23, ImageIO.read(new File(ASSETS_PATH + "jd.gif")));
      cardAssets.put(24, ImageIO.read(new File(ASSETS_PATH + "qd.gif")));
      cardAssets.put(25, ImageIO.read(new File(ASSETS_PATH + "kd.gif")));

      // hearts
      cardAssets.put(26, ImageIO.read(new File(ASSETS_PATH + "ah.gif")));
      for (int i = 2; i <= 10; i++) {
        cardAssets.put(i + 25, ImageIO.read(new File(ASSETS_PATH + i + "h.gif")));
      }
      cardAssets.put(36, ImageIO.read(new File(ASSETS_PATH + "jh.gif")));
      cardAssets.put(37, ImageIO.read(new File(ASSETS_PATH + "qh.gif")));
      cardAssets.put(38, ImageIO.read(new File(ASSETS_PATH + "kh.gif")));

      // spades
      cardAssets.put(39, ImageIO.read(new File(ASSETS_PATH + "as.gif")));
      for (int i = 2; i <= 10; i++) {
        cardAssets.put(i + 38, ImageIO.read(new File(ASSETS_PATH + i + "s.gif")));
      }
      cardAssets.put(49, ImageIO.read(new File(ASSETS_PATH + "js.gif")));
      cardAssets.put(50, ImageIO.read(new File(ASSETS_PATH + "qs.gif")));
      cardAssets.put(51, ImageIO.read(new File(ASSETS_PATH + "ks.gif")));
    } catch (IOException e) {
      System.out.println("Error loading assets");
      e.printStackTrace();
    }
  }

  // opens the menu window.
  // should only be called once per game instance
  public void openMenuWindow() {
    menuFrame = new MenuFrame();
    menuFrame.openWindow();
  }

  // closes the menu window.
  // should only be called once per game instance
  public void closeMenuWindow() {
    menuFrame.closeWindow();
  }

  // display a message in a simple dialog box within the dealer or menu window,
  // whichever is active
  public void message(String message) {
    if (dealerFrame == null) {
      JOptionPane.showMessageDialog(menuFrame, message);
      return;
    }

    JOptionPane.showMessageDialog(dealerFrame, message);
  }

  // opens the dealer window.
  // should only be called once per game instance
  public void openDealerWindow() {
    dealerFrame = new DealerFrame();
    dealerFrame.openWindow();
  }

  public void setBalance(double value) {
    dealerFrame.setBalance(value);
  }

  public void setBet(int value) {
    dealerFrame.setBet(value);
  }

  public void setDealerCards(List<Integer> cards) {
    dealerFrame.setDealerCards(cards);
  }

  public void setDealerPoints(int value) {
    dealerFrame.setDealerPoints(value);
  }
}
