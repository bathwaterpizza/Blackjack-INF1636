package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import controller.*;

// represents the main game menu
class MenuFrame extends JFrame {
  MenuFrame() {
    // frame setup
    setTitle("Blackjack Menu");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 200);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1, 10, 10));
    setResizable(false);
    setLocationRelativeTo(null); // appear on center

    JButton newGameButton = new JButton("New Game");
    JButton continueButton = new JButton("Continue");

    // increase text size
    Font buttonFont = new Font("Arial", Font.BOLD, 20);
    newGameButton.setFont(buttonFont);
    continueButton.setFont(buttonFont);

    // remove focus border
    newGameButton.setFocusPainted(false);
    continueButton.setFocusPainted(false);

    // listeners
    newGameButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // request controller to start a new game
        GameController.getAPI().startGame(false);
      }
    });
    continueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // choose a file and load the saved game
        chooseSaveFile();
      }
    });

    panel.add(newGameButton);
    panel.add(continueButton);

    getContentPane().add(panel, BorderLayout.CENTER);
  }

  // opens file chooser and requests a load
  private void chooseSaveFile() {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Select save file");
    chooser.setCurrentDirectory(new File("savefiles"));

    int userOption = chooser.showOpenDialog(this);
    if (userOption == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();

      GameController.getAPI().startGame(true);
      GameController.getAPI().requestLoad(file);
    }
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
