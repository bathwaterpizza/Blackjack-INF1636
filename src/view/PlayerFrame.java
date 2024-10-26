package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

// represents an instance of the dealer's frame
class PlayerFrame extends JFrame {
  // whether this frame is for the split hand or not
  private boolean split;

  PlayerFrame(boolean isSplit) {
    this.split = isSplit;
  }
}
