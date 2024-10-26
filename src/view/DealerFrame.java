package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import controller.*;
import model.Chip;

class DealerFrame extends JFrame implements MouseListener {
  private static final int FRAME_WIDTH = 704;
  private static final int FRAME_HEIGHT = 532;

  // references
  GameUI view = GameUI.getAPI();
  GameController controller = GameController.getAPI();

  // frame components
  private JLabel balanceLabel;
  private JLabel betLabel;

  // button bounds
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

  // chip bounds
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
  private int centerX = FRAME_WIDTH / 2;
  private int centerY = (FRAME_HEIGHT / 2) - 150;
  private int cardWidth = 95;
  private int cardHeight = 126;
  private int cardOffset = 40; // Y-space between stacked cards

  DealerFrame() {
    // init frame
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Blackjack - Dealer");
    setLayout(null); // manual positioning
    setResizable(false);

    // init labels
    balanceLabel = new JLabel("BAL: 0");
    balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
    balanceLabel.setBounds(3, FRAME_HEIGHT - 63, 120, 20);

    betLabel = new JLabel("BET: 0");
    betLabel.setFont(new Font("Arial", Font.BOLD, 16));
    betLabel.setBounds(3, FRAME_HEIGHT - 95, 120, 20);

    add(balanceLabel);
    add(betLabel);

    // register mouse listener
    addMouseListener(this);
  }

  // called on mouse click to verify button events
  @Override
  public void mouseClicked(MouseEvent e) {
    int mouseX = e.getX();
    int mouseY = e.getY();

    if (mouseX >= btnExitX && mouseX <= btnExitX + btnWidth &&
        mouseY >= btnExitY && mouseY <= btnExitY + btnHeight) {
      // Exit button clicked
      controller.requestExit();
    } else if (mouseX >= btnDoubleX && mouseX <= btnDoubleX + btnWidth &&
        mouseY >= btnDoubleY && mouseY <= btnDoubleY + btnHeight) {
      // Double button clicked
      controller.requestDouble();
    } else if (mouseX >= btnSplitX && mouseX <= btnSplitX + btnWidth &&
        mouseY >= btnSplitY && mouseY <= btnSplitY + btnHeight) {
      // Split button clicked
      controller.requestSplit();
    } else if (mouseX >= btnClearX && mouseX <= btnClearX + btnWidth &&
        mouseY >= btnClearY && mouseY <= btnClearY + btnHeight) {
      // Clear button clicked
      controller.requestClear();
    } else if (mouseX >= btnDealX && mouseX <= btnDealX + btnWidth &&
        mouseY >= btnDealY && mouseY <= btnDealY + btnHeight) {
      // Deal button clicked
      controller.requestDeal();
    } else if (mouseX >= btnHitX && mouseX <= btnHitX + btnWidth &&
        mouseY >= btnHitY && mouseY <= btnHitY + btnHeight) {
      // Hit button clicked
      controller.requestHit();
    } else if (mouseX >= btnStandX && mouseX <= btnStandX + btnWidth &&
        mouseY >= btnStandY && mouseY <= btnStandY + btnHeight) {
      // Stand button clicked
      controller.requestStand();
    } else if (mouseX >= btnSurrenderX && mouseX <= btnSurrenderX + btnWidth &&
        mouseY >= btnSurrenderY && mouseY <= btnSurrenderY + btnHeight) {
      // Surrender button clicked
      controller.requestSurrender();
    } else if (mouseX >= chip1X && mouseX <= chip1X + chipWidth &&
        mouseY >= chip1Y && mouseY <= chip1Y + chipHeight) {
      // Chip 1 clicked
      if (SwingUtilities.isLeftMouseButton(e)) {
        // increment bet
        controller.requestRaiseBet(Chip.BLACK);
      } else if (SwingUtilities.isRightMouseButton(e)) {
        // decrement bet
        controller.requestLowerBet(Chip.BLACK);
      }
    } else if (mouseX >= chip5X && mouseX <= chip5X + chipWidth &&
        mouseY >= chip5Y && mouseY <= chip5Y + chipHeight) {
      // Chip 5 clicked
      if (SwingUtilities.isLeftMouseButton(e)) {
        // increment bet
        controller.requestRaiseBet(Chip.PINK);
      } else if (SwingUtilities.isRightMouseButton(e)) {
        // decrement bet
        controller.requestLowerBet(Chip.PINK);
      }
    } else if (mouseX >= chip10X && mouseX <= chip10X + chipWidth &&
        mouseY >= chip10Y && mouseY <= chip10Y + chipHeight) {
      // Chip 10 clicked
      if (SwingUtilities.isLeftMouseButton(e)) {
        // increment bet
        controller.requestRaiseBet(Chip.BLUE);
      } else if (SwingUtilities.isRightMouseButton(e)) {
        // decrement bet
        controller.requestLowerBet(Chip.BLUE);
      }
    } else if (mouseX >= chip20X && mouseX <= chip20X + chipWidth &&
        mouseY >= chip20Y && mouseY <= chip20Y + chipHeight) {
      // Chip 20 clicked
      if (SwingUtilities.isLeftMouseButton(e)) {
        // increment bet
        controller.requestRaiseBet(Chip.RED);
      } else if (SwingUtilities.isRightMouseButton(e)) {
        // decrement bet
        controller.requestLowerBet(Chip.RED);
      }
    } else if (mouseX >= chip50X && mouseX <= chip50X + chipWidth &&
        mouseY >= chip50Y && mouseY <= chip50Y + chipHeight) {
      // Chip 50 clicked
      if (SwingUtilities.isLeftMouseButton(e)) {
        // increment bet
        controller.requestRaiseBet(Chip.GREEN);
      } else if (SwingUtilities.isRightMouseButton(e)) {
        // decrement bet
        controller.requestLowerBet(Chip.GREEN);
      }
    } else if (mouseX >= chip100X && mouseX <= chip100X + chipWidth &&
        mouseY >= chip100Y && mouseY <= chip100Y + chipHeight) {
      // Chip 100 clicked
      if (SwingUtilities.isLeftMouseButton(e)) {
        // increment bet
        controller.requestRaiseBet(Chip.GOLD);
      } else if (SwingUtilities.isRightMouseButton(e)) {
        // decrement bet
        controller.requestLowerBet(Chip.GOLD);
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
      int posX = centerX - (cardWidth / 2);
      int posY = centerY - (cardHeight / 2) + (i * cardOffset);

      g.drawImage(view.cardAssets.get(dealerCards.get(i)), posX, posY, cardWidth, cardHeight, this);
    }
  }

  // render everything
  @Override
  public void paint(Graphics g) {
    super.paint(g);

    // paint table
    g.drawImage(view.tableAsset, 0, 0, getWidth(), getHeight(), this);

    // paint labels
    balanceLabel.repaint();
    betLabel.repaint();

    // draw chip images
    g.drawImage(view.chipAssets.get(Chip.BLACK), chip1X, chip1Y, chipWidth, chipHeight, this);
    g.drawImage(view.chipAssets.get(Chip.PINK), chip5X, chip5Y, chipWidth, chipHeight, this);
    g.drawImage(view.chipAssets.get(Chip.BLUE), chip10X, chip10Y, chipWidth, chipHeight, this);
    g.drawImage(view.chipAssets.get(Chip.RED), chip20X, chip20Y, chipWidth, chipHeight, this);
    g.drawImage(view.chipAssets.get(Chip.GREEN), chip50X, chip50Y, chipWidth, chipHeight, this);
    g.drawImage(view.chipAssets.get(Chip.GOLD), chip100X, chip100Y, chipWidth, chipHeight, this);

    // draw hand
    drawStackedCards(g);

    // draw button areas for reference
    g.setColor(Color.RED);
    g.drawRect(btnExitX, btnExitY, btnWidth, btnHeight);
    g.drawRect(btnDoubleX, btnDoubleY, btnWidth, btnHeight);
    g.drawRect(btnSplitX, btnSplitY, btnWidth, btnHeight);
    g.drawRect(btnClearX, btnClearY, btnWidth, btnHeight);
    g.drawRect(btnDealX, btnDealY, btnWidth, btnHeight);
    g.drawRect(btnHitX, btnHitY, btnWidth, btnHeight);
    g.drawRect(btnStandX, btnStandY, btnWidth, btnHeight);
    g.drawRect(btnSurrenderX, btnSurrenderY, btnWidth, btnHeight);

    // draw chip areas for reference
    g.drawRect(chip1X, chip1Y, chipWidth, chipHeight);
    g.drawRect(chip5X, chip5Y, chipWidth, chipHeight);
    g.drawRect(chip10X, chip10Y, chipWidth, chipHeight);
    g.drawRect(chip20X, chip20Y, chipWidth, chipHeight);
    g.drawRect(chip50X, chip50Y, chipWidth, chipHeight);
    g.drawRect(chip100X, chip100Y, chipWidth, chipHeight);
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
  void setDealerCards(List<Integer> cards) {
    dealerCards = cards;
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
