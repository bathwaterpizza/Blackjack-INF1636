package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import controller.*;
import model.Chip; // enum Chip

// represents the dealer's window in the UI
class DealerFrame extends JFrame implements MouseListener {
  private static final int FRAME_WIDTH = 704;
  private static final int FRAME_HEIGHT = 532;

  // frame components
  private JLabel balanceLabel;
  private JLabel betLabel;
  private JLabel pointsLabel;

  // button size and bounds
  private int btnWidth = 96;
  private int btnHeight = 34;

  // exit
  private int btnExitX = 20;
  private int btnExitY = 412;
  // double
  private int btnDoubleX = 150;
  private int btnDoubleY = 489;
  // split
  private int btnSplitX = 255;
  private int btnSplitY = 489;
  // clear
  private int btnClearX = 360;
  private int btnClearY = 489;
  // deal
  private int btnDealX = 465;
  private int btnDealY = 489;
  // hit
  private int btnHitX = 591;
  private int btnHitY = 408;
  // stand
  private int btnStandX = 591;
  private int btnStandY = 448;
  // surrender
  private int btnSurrenderX = 591;
  private int btnSurrenderY = 489;

  // chip size and bounds
  private int chipWidth = 60;
  private int chipHeight = 60;

  // 1
  private int chip1X = 150;
  private int chip1Y = 412;
  // 5
  private int chip5X = 222;
  private int chip5Y = 412;
  // 10
  private int chip10X = 294;
  private int chip10Y = 412;
  // 20
  private int chip20X = 366;
  private int chip20Y = 412;
  // 50
  private int chip50X = 438;
  private int chip50Y = 412;
  // 100
  private int chip100X = 510;
  private int chip100Y = 412;

  // cards
  private List<Integer> dealerCards = null;
  private int cardCenterX = FRAME_WIDTH / 2;
  private int cardCenterY = (FRAME_HEIGHT / 2) - 150;
  private int cardWidth = 95;
  private int cardHeight = 126;
  private int cardOffset = 40; // Y-space between stacked cards

  DealerFrame() {
    // init frame
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Dealer");
    setLayout(null); // manual positioning
    setResizable(false);
    setLocationRelativeTo(null); // appear on center

    // init labels
    balanceLabel = new JLabel("BAL: 0");
    balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
    balanceLabel.setBounds(3, FRAME_HEIGHT - 63, 121, 20);

    betLabel = new JLabel("BET: 0");
    betLabel.setFont(new Font("Arial", Font.BOLD, 16));
    betLabel.setBounds(3, FRAME_HEIGHT - 95, 121, 20);

    pointsLabel = new JLabel("VALUE: 0");
    pointsLabel.setFont(new Font("Arial", Font.BOLD, 16));
    pointsLabel.setBounds(cardCenterX - 160, 74, 84, 20);

    add(balanceLabel);
    add(betLabel);
    add(pointsLabel);

    // register mouse listener
    addMouseListener(this);
  }

  // check if the mouse is within the area of a game choice button
  private boolean isInsideButton(MouseEvent event, int btnX, int btnY) {
    int mouseX = event.getX(), mouseY = event.getY();

    return (mouseX >= btnX) && (mouseX <= (btnX + btnWidth)) &&
        (mouseY >= btnY) && (mouseY <= (btnY + btnHeight));
  }

  // check if the mouse is within the area of a chip button
  private boolean isInsideChip(MouseEvent event, int chipX, int chipY) {
    int mouseX = event.getX(), mouseY = event.getY();

    return (mouseX >= chipX) && (mouseX <= (chipX + chipWidth)) &&
        (mouseY >= chipY) && (mouseY <= (chipY + chipHeight));
  }

  // called on mouse click to verify button events
  @Override
  public void mouseClicked(MouseEvent event) {
    if (isInsideButton(event, btnExitX, btnExitY)) {
      // Exit button clicked
      GameController.getAPI().requestExit();
    } else if (isInsideButton(event, btnDoubleX, btnDoubleY)) {
      // Double button clicked
      GameController.getAPI().requestDouble();
    } else if (isInsideButton(event, btnSplitX, btnSplitY)) {
      // Split button clicked
      GameController.getAPI().requestSplit();
    } else if (isInsideButton(event, btnClearX, btnClearY)) {
      // Clear button clicked
      GameController.getAPI().requestClear();
    } else if (isInsideButton(event, btnDealX, btnDealY)) {
      // Deal button clicked
      GameController.getAPI().requestDeal();
    } else if (isInsideButton(event, btnHitX, btnHitY)) {
      // Hit button clicked
      GameController.getAPI().requestHit();
    } else if (isInsideButton(event, btnStandX, btnStandY)) {
      // Stand button clicked
      GameController.getAPI().requestStand();
    } else if (isInsideButton(event, btnSurrenderX, btnSurrenderY)) {
      // Surrender button clicked
      GameController.getAPI().requestSurrender();
    } else if (isInsideChip(event, chip1X, chip1Y)) {
      // Chip 1 clicked
      if (SwingUtilities.isLeftMouseButton(event)) {
        // Increment bet
        GameController.getAPI().requestRaiseBet(Chip.BLACK);
      } else if (SwingUtilities.isRightMouseButton(event)) {
        // Decrement bet
        GameController.getAPI().requestLowerBet(Chip.BLACK);
      }
    } else if (isInsideChip(event, chip5X, chip5Y)) {
      // Chip 5 clicked
      if (SwingUtilities.isLeftMouseButton(event)) {
        // Increment bet
        GameController.getAPI().requestRaiseBet(Chip.PINK);
      } else if (SwingUtilities.isRightMouseButton(event)) {
        // Decrement bet
        GameController.getAPI().requestLowerBet(Chip.PINK);
      }
    } else if (isInsideChip(event, chip10X, chip10Y)) {
      // Chip 10 clicked
      if (SwingUtilities.isLeftMouseButton(event)) {
        // Increment bet
        GameController.getAPI().requestRaiseBet(Chip.BLUE);
      } else if (SwingUtilities.isRightMouseButton(event)) {
        // Decrement bet
        GameController.getAPI().requestLowerBet(Chip.BLUE);
      }
    } else if (isInsideChip(event, chip20X, chip20Y)) {
      // Chip 20 clicked
      if (SwingUtilities.isLeftMouseButton(event)) {
        // Increment bet
        GameController.getAPI().requestRaiseBet(Chip.RED);
      } else if (SwingUtilities.isRightMouseButton(event)) {
        // Decrement bet
        GameController.getAPI().requestLowerBet(Chip.RED);
      }
    } else if (isInsideChip(event, chip50X, chip50Y)) {
      // Chip 50 clicked
      if (SwingUtilities.isLeftMouseButton(event)) {
        // Increment bet
        GameController.getAPI().requestRaiseBet(Chip.GREEN);
      } else if (SwingUtilities.isRightMouseButton(event)) {
        // Decrement bet
        GameController.getAPI().requestLowerBet(Chip.GREEN);
      }
    } else if (isInsideChip(event, chip100X, chip100Y)) {
      // Chip 100 clicked
      if (SwingUtilities.isLeftMouseButton(event)) {
        // Increment bet
        GameController.getAPI().requestRaiseBet(Chip.GOLD);
      } else if (SwingUtilities.isRightMouseButton(event)) {
        // Decrement bet
        GameController.getAPI().requestLowerBet(Chip.GOLD);
      }
    }
  }

  // overriding some methods that we won't use from the interface
  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  // draw the cards from a hand in a stacked layout
  public void drawStackedCards(Graphics g) {
    if (dealerCards == null)
      return;

    for (int i = 0; i < dealerCards.size(); i++) {
      int posX = cardCenterX - (cardWidth / 2);
      int posY = cardCenterY - (cardHeight / 2) + (i * cardOffset);

      g.drawImage(GameUI.getAPI().cardAssets.get(dealerCards.get(i)), posX, posY, cardWidth, cardHeight, this);
    }
  }

  // render everything
  @Override
  public void paint(Graphics g) {
    super.paint(g);

    // paint table
    g.drawImage(GameUI.getAPI().tableAsset, 0, 0, getWidth(), getHeight(), this);

    // paint labels
    balanceLabel.repaint();
    betLabel.repaint();
    pointsLabel.repaint();

    // draw chip images
    g.drawImage(GameUI.getAPI().chipAssets.get(Chip.BLACK), chip1X, chip1Y, chipWidth, chipHeight, this);
    g.drawImage(GameUI.getAPI().chipAssets.get(Chip.PINK), chip5X, chip5Y, chipWidth, chipHeight, this);
    g.drawImage(GameUI.getAPI().chipAssets.get(Chip.BLUE), chip10X, chip10Y, chipWidth, chipHeight, this);
    g.drawImage(GameUI.getAPI().chipAssets.get(Chip.RED), chip20X, chip20Y, chipWidth, chipHeight, this);
    g.drawImage(GameUI.getAPI().chipAssets.get(Chip.GREEN), chip50X, chip50Y, chipWidth, chipHeight, this);
    g.drawImage(GameUI.getAPI().chipAssets.get(Chip.GOLD), chip100X, chip100Y, chipWidth, chipHeight, this);

    // draw hand
    drawStackedCards(g);

    // draw button areas for reference
    // g.setColor(Color.RED);
    // g.drawRect(btnExitX, btnExitY, btnWidth, btnHeight);
    // g.drawRect(btnDoubleX, btnDoubleY, btnWidth, btnHeight);
    // g.drawRect(btnSplitX, btnSplitY, btnWidth, btnHeight);
    // g.drawRect(btnClearX, btnClearY, btnWidth, btnHeight);
    // g.drawRect(btnDealX, btnDealY, btnWidth, btnHeight);
    // g.drawRect(btnHitX, btnHitY, btnWidth, btnHeight);
    // g.drawRect(btnStandX, btnStandY, btnWidth, btnHeight);
    // g.drawRect(btnSurrenderX, btnSurrenderY, btnWidth, btnHeight);

    // draw chip areas for reference
    // g.drawRect(chip1X, chip1Y, chipWidth, chipHeight);
    // g.drawRect(chip5X, chip5Y, chipWidth, chipHeight);
    // g.drawRect(chip10X, chip10Y, chipWidth, chipHeight);
    // g.drawRect(chip20X, chip20Y, chipWidth, chipHeight);
    // g.drawRect(chip50X, chip50Y, chipWidth, chipHeight);
    // g.drawRect(chip100X, chip100Y, chipWidth, chipHeight);
  }

  // update balance text
  void setBalance(double value) {
    balanceLabel.setText(String.format("BAL: %.2f", value));
    repaint();
  }

  // update balance text
  void setBet(int value) {
    betLabel.setText(String.format("BET: %d", value));
    repaint();
  }

  // update dealer hand
  void setCards(List<Integer> cards) {
    dealerCards = cards;
    repaint();
  }

  // update dealer hand value
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
