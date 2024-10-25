package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DealerFrame extends JFrame implements MouseListener {
  private static final int FRAME_WIDTH = 704;
  private static final int FRAME_HEIGHT = 532;

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

  DealerFrame() {
    // init frame
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Dealer Window");
    setLayout(null); // manual positioning

    // init labels
    balanceLabel = new JLabel("BAL: 0");
    balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
    balanceLabel.setBounds(3, FRAME_HEIGHT - 63, 120, 20);

    betLabel = new JLabel("BET: 0");
    betLabel.setFont(new Font("Arial", Font.BOLD, 16));
    betLabel.setBounds(3, FRAME_HEIGHT - 95, 120, 20);

    // labels
    add(balanceLabel);
    add(betLabel);

    // mouse click listener
    addMouseListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int mouseX = e.getX();
    int mouseY = e.getY();

    if (mouseX >= btnExitX && mouseX <= btnExitX + btnWidth &&
        mouseY >= btnExitY && mouseY <= btnExitY + btnHeight) {
      System.out.println("Exit button clicked!");
    } else if (mouseX >= btnDoubleX && mouseX <= btnDoubleX + btnWidth &&
        mouseY >= btnDoubleY && mouseY <= btnDoubleY + btnHeight) {
      System.out.println("Double button clicked!");
    } else if (mouseX >= btnSplitX && mouseX <= btnSplitX + btnWidth &&
        mouseY >= btnSplitY && mouseY <= btnSplitY + btnHeight) {
      System.out.println("Split button clicked!");
    } else if (mouseX >= btnClearX && mouseX <= btnClearX + btnWidth &&
        mouseY >= btnClearY && mouseY <= btnClearY + btnHeight) {
      System.out.println("Clear button clicked!");
    } else if (mouseX >= btnDealX && mouseX <= btnDealX + btnWidth &&
        mouseY >= btnDealY && mouseY <= btnDealY + btnHeight) {
      System.out.println("Deal button clicked!");
    } else if (mouseX >= btnHitX && mouseX <= btnHitX + btnWidth &&
        mouseY >= btnHitY && mouseY <= btnHitY + btnHeight) {
      System.out.println("Hit button clicked!");
    } else if (mouseX >= btnStandX && mouseX <= btnStandX + btnWidth &&
        mouseY >= btnStandY && mouseY <= btnStandY + btnHeight) {
      System.out.println("Stand button clicked!");
    } else if (mouseX >= btnSurrenderX && mouseX <= btnSurrenderX + btnWidth &&
        mouseY >= btnSurrenderY && mouseY <= btnSurrenderY + btnHeight) {
      System.out.println("Surrender button clicked!");
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

  // render everything
  @Override
  public void paint(Graphics g) {
    super.paint(g);

    // paint table
    g.drawImage(GameUI.getAPI().tableAsset, 0, 0, getWidth(), getHeight(), this);

    // paint labels
    balanceLabel.repaint();
    betLabel.repaint();

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
  }

  // update balance text
  void setBalance(double value) {
    balanceLabel.setText(String.format("BAL: %.2f", value));
  }

  // update balance text
  void setBet(int value) {
    betLabel.setText(String.format("BET: %d", value));
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
