package view;

import javax.swing.*;
import java.awt.*;

import controller.*;

// represents an instance of the dealer's frame
class DealerFrame extends JFrame {
  private static final int FRAME_WIDTH = 704;
  private static final int FRAME_HEIGHT = 532;

  // frame components
  private JLabel balanceLabel;

  DealerFrame() {
    // Set the frame size
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null); // Set layout to null for manual positioning

    // Initialize the JLabel
    balanceLabel = new JLabel("Value: 0");
    balanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    balanceLabel.setBounds(10, FRAME_HEIGHT - 50, 100, 30); // Manually position the JLabel

    // Add the JLabel to the frame
    add(balanceLabel);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    // Draw the image to fit the frame
    g.drawImage(GameUI.getAPI().tableAsset, 0, 0, getWidth(), getHeight(), this);
  }

  // Method to update the value displayed in the JLabel
  public void setBalance(int value) {
    balanceLabel.setText("Value: " + value);
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
