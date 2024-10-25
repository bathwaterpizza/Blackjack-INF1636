package view;

import controller.*;

// represents an instance of the dealer's frame
class PlayerFrame {
  // view instance
  private GameUI view;
  // whether this frame is for the split hand or not
  private boolean split;

  PlayerFrame(GameUI view, boolean isSplit) {
    this.view = view;
    this.split = isSplit;
  }
}
