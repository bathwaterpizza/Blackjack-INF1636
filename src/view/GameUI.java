package view;

import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

// importing enums and records from model, but no classes
import model.Chip;
import model.RoundResult;
import model.GameState;

import observer.*;

// public class that contains the view API and assets
public class GameUI implements IGameObserver {
  // singleton instance
  private static GameUI instance = null;

  // asset properties
  private static final String ASSETS_PATH = "src/view/assets/";
  HashMap<Integer, Image> cardAssets = new HashMap<Integer, Image>();
  HashMap<Chip, Image> chipAssets = new HashMap<Chip, Image>();
  Image tableAsset;

  // UI frame properties
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

  // observer interface methods
  public void updateHand(IGameObservable observable, GameState state) {
    // dealer window
    setDealerCards(state.dealerCards());
    setDealerPoints(state.dealerPoints());

    // player windows
    setPlayerCards(false, state.playerCards());
    setPlayerPoints(false, state.playerPoints());
    // split
    setPlayerCards(true, state.playerSplitCards());
    setPlayerPoints(true, state.playerSplitPoints());
    setPlayingHand(state.splitPlaying());
  }

  public void updateMoney(IGameObservable observable, GameState state) {
    // dealer window
    setBalance(state.balance());
    setTotalBet(state.totalBet());

    // player windows
    setHandBet(false, state.bet());
    // split
    setHandBet(true, state.splitBet());
  }

  public void updateRoundResult(IGameObservable observable, RoundResult result, RoundResult splitResult) {
    messageHand(false, result.toString());

    if (splitResult != null)
      messageHand(true, splitResult.toString());
  }

  public void updateWindows(IGameObservable observable, boolean playerWindowOpen, boolean playerSplitWindowOpen) {
    if (playerWindowOpen) {
      openPlayerWindow(false);
    } else {
      closePlayerWindow(false);
    }

    if (playerSplitWindowOpen) {
      openPlayerWindow(true);
    } else {
      closePlayerWindow(true);
    }
  }

  // loads all the game images into memory
  private void loadAssets() {
    try {
      // table asset
      tableAsset = ImageIO.read(new File(ASSETS_PATH + "table.png"));

      // chip assets
      for (Chip chip : Chip.values()) {
        chipAssets.put(chip, ImageIO.read(new File(ASSETS_PATH + "chip" + chip.toInt() + ".png")));
      }

      // card assets,
      // these int values match the toInt and fromInt methods of the Card class.

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

  // display a dialog box message within the dealer or menu window,
  // whichever is active
  public void messageGame(String message) {
    if (dealerFrame == null) {
      JOptionPane.showMessageDialog(menuFrame, message);
      return;
    }

    JOptionPane.showMessageDialog(dealerFrame, message);
  }

  // display a dialog box message within the player hand window
  public void messageHand(boolean isSplit, String message) {
    if (isSplit && splitPlayerFrame != null) {
      JOptionPane.showMessageDialog(splitPlayerFrame, message);
    } else if (!isSplit && playerFrame != null) {
      JOptionPane.showMessageDialog(playerFrame, message);
    }
  }

  // opens the dealer window.
  // should only be called once per game instance
  public void openDealerWindow() {
    dealerFrame = new DealerFrame();
    dealerFrame.openWindow();
  }

  // opens the player window
  public void openPlayerWindow(boolean isSplit) {
    if (isSplit) {
      if (splitPlayerFrame != null)
        return;

      splitPlayerFrame = new PlayerFrame(isSplit);
      splitPlayerFrame.openWindow();
    } else {
      if (playerFrame != null)
        return;

      playerFrame = new PlayerFrame(isSplit);
      playerFrame.openWindow();
    }
  }

  // closes the player window
  public void closePlayerWindow(boolean isSplit) {
    if (isSplit) {
      if (splitPlayerFrame == null)
        return;

      splitPlayerFrame.closeWindow();
      splitPlayerFrame = null;
    } else {
      if (playerFrame == null)
        return;

      playerFrame.closeWindow();
      playerFrame = null;
    }
  }

  /*
   * UI internal setter methods
   * these methods are called by the observer interface methods
   */
  // set money labels displayed
  private void setBalance(double value) {
    dealerFrame.setBalance(value);
  }

  private void setTotalBet(int value) {
    dealerFrame.setBet(value);
  }

  private void setHandBet(boolean isSplit, int value) {
    if (isSplit) {
      if (splitPlayerFrame == null)
        return;

      splitPlayerFrame.setBet(value);
    } else {
      if (playerFrame == null)
        return;

      playerFrame.setBet(value);
    }
  }

  // set stacked cards displayed, and the hand value
  private void setDealerCards(List<Integer> cards) {
    dealerFrame.setCards(cards);
  }

  private void setDealerPoints(int value) {
    dealerFrame.setPoints(value);
  }

  private void setPlayerCards(boolean isSplit, List<Integer> cards) {
    if (isSplit) {
      if (splitPlayerFrame == null)
        return;

      splitPlayerFrame.setCards(cards);
    } else {
      if (playerFrame == null)
        return;

      playerFrame.setCards(cards);
    }
  }

  private void setPlayerPoints(boolean isSplit, int value) {
    if (isSplit) {
      if (splitPlayerFrame == null)
        return;

      splitPlayerFrame.setPoints(value);
    } else {
      if (playerFrame == null)
        return;

      playerFrame.setPoints(value);
    }
  }

  // set which hand is playing
  private void setPlayingHand(boolean isSplitPlaying) {
    if (isSplitPlaying) {
      if (playerFrame != null)
        playerFrame.setPlaying(false);

      if (splitPlayerFrame != null)
        splitPlayerFrame.setPlaying(true);
    } else {
      if (playerFrame != null)
        playerFrame.setPlaying(true);

      if (splitPlayerFrame != null)
        splitPlayerFrame.setPlaying(false);
    }
  }
}
