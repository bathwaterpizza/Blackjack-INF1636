package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.*;

// represents an instance of the dealer's frame
class PlayerFrame extends JFrame {
  private static final int FRAME_WIDTH = 300;
  private static final int FRAME_HEIGHT = 500;

  // instances
  GameUI view = GameUI.getAPI();
  GameController controller = GameController.getAPI();

  // whether this frame is for the split hand or not
  private boolean split;

  // frame components
  private JLabel betLabel;
  private JLabel pointsLabel;

  // cards
  private List<Integer> handCards = null;
  private int cardCenterX = FRAME_WIDTH / 2;
  private int cardCenterY = (FRAME_HEIGHT / 2) - 100;
  private int cardWidth = 95;
  private int cardHeight = 126;
  private int cardOffset = 40; // Y-space between stacked cards

  PlayerFrame(boolean isSplit) {
    this.split = isSplit;

    // init frame
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    if (split) {
      setTitle("Blackjack - Split Hand");
    } else {
      setTitle("Blackjack - Main Hand");
    }
    setLayout(null); // manual positioning
    setResizable(false);

    // init labels
    betLabel = new JLabel("HAND BET: 0");
    betLabel.setFont(new Font("Arial", Font.BOLD, 16));
    betLabel.setBounds(3, FRAME_HEIGHT - 95, 150, 20);

    pointsLabel = new JLabel("VALUE: 0");
    pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
    pointsLabel.setBounds(cardCenterX - 160, 74, 84, 20);

    add(betLabel);
    add(pointsLabel);
  }

  // draw the cards from a hand in a stacked layout
  public void drawStackedCards(Graphics g) {
    if (handCards == null)
      return;

    for (int i = 0; i < handCards.size(); i++) {
      int posX = cardCenterX - (cardWidth / 2);
      int posY = cardCenterY - (cardHeight / 2) + (i * cardOffset);

      g.drawImage(view.cardAssets.get(handCards.get(i)), posX, posY, cardWidth, cardHeight, this);
    }
  }

  // render everything
  @Override
  public void paint(Graphics g) {
    super.paint(g);

    // paint labels
    betLabel.repaint();
    pointsLabel.repaint();

    // draw hand
    drawStackedCards(g);
  }

  // update balance text
  void setBet(int value) {
    betLabel.setText(String.format("HAND BET: %d", value));
    repaint();
  }

  // update dealer hand
  void setCards(List<Integer> cards) {
    handCards = cards;
    repaint();
  }

  void setPoints(int value) {
    pointsLabel.setText(String.format("VALUE: %d", value));
    repaint();
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
