package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import model.Chip;

// public class that contains the view API
public class GameUI {
  // singleton instance
  private static GameUI instance = null;

  // asset properties
  HashMap<Integer, Image> cardAssets = new HashMap<Integer, Image>();
  HashMap<Chip, Image> chipAssets = new HashMap<Chip, Image>();
  Image tableAsset;

  // UI properties
  MenuFrame menu;
  DealerFrame dealer;

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
      tableAsset = ImageIO.read(new File("assets/table.png"));

      // chip assets
      chipAssets.put(Chip.BLACK, ImageIO.read(new File("assets/chip1.png")));
      chipAssets.put(Chip.PINK, ImageIO.read(new File("assets/chip5.png")));
      chipAssets.put(Chip.BLUE, ImageIO.read(new File("assets/chip10.png")));
      chipAssets.put(Chip.RED, ImageIO.read(new File("assets/chip20.png")));
      chipAssets.put(Chip.GREEN, ImageIO.read(new File("assets/chip50.png")));
      chipAssets.put(Chip.GOLD, ImageIO.read(new File("assets/chip100.png")));

      // card assets,
      // these int values match the toInt and fromInt methods of the Card class

      // clubs
      cardAssets.put(0, ImageIO.read(new File("assets/ac.png")));
      for (int i = 2; i <= 10; i++) {
        cardAssets.put(i - 1, ImageIO.read(new File("assets/" + i + "c.png")));
      }
      cardAssets.put(10, ImageIO.read(new File("assets/jc.png")));
      cardAssets.put(11, ImageIO.read(new File("assets/qc.png")));
      cardAssets.put(12, ImageIO.read(new File("assets/kc.png")));

      // diamonds
      cardAssets.put(13, ImageIO.read(new File("assets/ad.png")));
      for (int i = 2; i <= 10; i++) {
        cardAssets.put(i + 12, ImageIO.read(new File("assets/" + i + "d.png")));
      }
      cardAssets.put(23, ImageIO.read(new File("assets/jd.png")));
      cardAssets.put(24, ImageIO.read(new File("assets/qd.png")));
      cardAssets.put(25, ImageIO.read(new File("assets/kd.png")));

      // hearts
      cardAssets.put(26, ImageIO.read(new File("assets/ah.png")));
      for (int i = 2; i <= 10; i++) {
        cardAssets.put(i + 25, ImageIO.read(new File("assets/" + i + "h.png")));
      }
      cardAssets.put(36, ImageIO.read(new File("assets/jh.png")));
      cardAssets.put(37, ImageIO.read(new File("assets/qh.png")));
      cardAssets.put(38, ImageIO.read(new File("assets/kh.png")));

      // spades
      cardAssets.put(39, ImageIO.read(new File("assets/as.png")));
      for (int i = 2; i <= 10; i++) {
        cardAssets.put(i + 38, ImageIO.read(new File("assets/" + i + "s.png")));
      }
      cardAssets.put(49, ImageIO.read(new File("assets/js.png")));
      cardAssets.put(50, ImageIO.read(new File("assets/qs.png")));
      cardAssets.put(51, ImageIO.read(new File("assets/ks.png")));
    } catch (IOException e) {
      System.out.println("Error loading assets");
      e.printStackTrace();
    }
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
