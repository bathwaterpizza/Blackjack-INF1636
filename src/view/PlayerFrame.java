package view;

import controller.*;

// represents an instance of the dealer's frame
class PlayerFrame {
  // whether this frame is for the split hand or not
  private boolean split;

  PlayerFrame(boolean isSplit) {
    this.split = isSplit;
  }
}
