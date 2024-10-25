package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GameController;

// creates the main game menu
class MenuFrame extends JFrame {
  MenuFrame() {
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

        // informs the controller we want to start a new game
        GameController.getAPI().newGame();
      }
    });
    continueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO:Save/Continue game feature
        System.out.println("Continue button clicked");
      }
    });

    // panel setup
    panel.add(newGameButton);
    panel.add(continueButton);

    getContentPane().add(panel, BorderLayout.CENTER);
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
