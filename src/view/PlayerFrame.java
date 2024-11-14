package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// represents an instance of the dealer's frame
class PlayerFrame extends JFrame {
  private static final int FRAME_WIDTH = 250;
  private static final int FRAME_HEIGHT = 450;

  // whether this frame is for the split hand or not
  private boolean split;
  // whether this hand is playing or not, for the arrow indicator
  private boolean playing = false;

  // frame components
  private JLabel betLabel;
  private JLabel pointsLabel;

  // cards
  private List<Integer> handCards = null;
  private int cardCenterX = FRAME_WIDTH / 2 + 50;
  private int cardCenterY = (FRAME_HEIGHT / 2) - 115;
  private int cardWidth = 95;
  private int cardHeight = 126;
  private int cardOffset = 40; // Y-space between stacked cards

  PlayerFrame(boolean isSplit) {
    this.split = isSplit;

    // init frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // set title and location conditionally
    if (split) {
      setTitle("Split Hand");
      setLocation(screenSize.width - FRAME_WIDTH, (screenSize.height / 2) - (FRAME_HEIGHT / 2));
    } else {
      setTitle("Main Hand");
      setLocation(0, (screenSize.height / 2) - (FRAME_HEIGHT / 2));
    }
    setLayout(null); // manual positioning
    setResizable(false);

    // init labels
    betLabel = new JLabel("HAND BET: 0");
    betLabel.setFont(new Font("Arial", Font.BOLD, 16));
    betLabel.setBounds(40, FRAME_HEIGHT - 60, 150, 20);

    pointsLabel = new JLabel("VALUE: 0");
    pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
    pointsLabel.setBounds(15, 70, 84, 20);

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

      g.drawImage(GameUI.getAPI().cardAssets.get(handCards.get(i)), posX, posY, cardWidth, cardHeight, this);
    }
  }

  // draw a yellow arrow to indicate the hand that is playing
  private void drawRightArrow(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLUE);
    int arrowBaseX = 45; // Adjusted to be more to the left
    int arrowBaseY = 70; // Adjusted to be lower in the frame
    int arrowSize = 40;

    // three points of the triangle
    int[] xPoints = { arrowBaseX, arrowBaseX + arrowSize, arrowBaseX };
    int[] yPoints = { arrowBaseY - arrowSize / 2, arrowBaseY, arrowBaseY + arrowSize / 2 };

    g2d.fillPolygon(xPoints, yPoints, 3);
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

    // draw playing indicator arrow
    if (playing)
      drawRightArrow(g);
  }

  // update balance text
  void setBet(int value) {
    betLabel.setText(String.format("HAND BET: %d", value));
    repaint();
  }

  // update hand cards
  void setCards(List<Integer> cards) {
    handCards = cards;
    repaint();
  }

  // update hand points
  void setPoints(int value) {
    pointsLabel.setText(String.format("VALUE: %d", value));
    repaint();
  }

  void setPlaying(boolean isPlaying) {
    playing = isPlaying;
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
