package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// creates the main game menu
public class MenuFrame extends JFrame {
  public MenuFrame() {
    // frame setup
    setTitle("Menu");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 200);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1, 10, 10));

    JButton newGameButton = new JButton("New Game");
    JButton continueButton = new JButton("Continue");

    // listeners
    newGameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("New Game button clicked");
      }
    });
    continueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Continue button clicked");
      }
    });

    // panel setup
    panel.add(newGameButton);
    panel.add(continueButton);

    getContentPane().add(panel, BorderLayout.CENTER);
  }

  // Make the frame visible
  public void spawnMenu() {
    this.setVisible(true);
  }

  // Dispose of the frame
  public void closeMenu() {
    this.dispose();
  }
}
