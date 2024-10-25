package view;

import javax.swing.*;
import java.awt.*;

import controller.*;

// represents an instance of the dealer's frame
class DealerFrame extends JFrame {
  private static final int FRAME_WIDTH = 704;
  private static final int FRAME_HEIGHT = 532;

  // view instance
  private GameUI view;

  DealerFrame(GameUI view) {
    this.view = view;

    // Set the frame size
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    g.drawImage(GameUI.getAPI().tableAsset, 0, 0, getWidth(), getHeight(), this);
  }

  // Make the frame visible
  void openWindow() {
    this.setVisible(true);
  }

  // Dispose of the frame
  void closeWindow() {
    this.dispose();
  }
}
