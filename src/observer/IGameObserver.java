package observer;

import model.GameState; // record
import model.RoundResult; // enum

// Interface for the class that will be observing
public interface IGameObserver {
  // updates the cards in each hand, their value, and whether the split hand is
  // playing
  public void updateHand(IGameObservable observable, GameState state);

  // updates the balance, total bet (dealer window) and hand bet (player windows)
  public void updateMoney(IGameObservable observable, GameState state);

  // displays a dialog box with the round result for each hand
  public void updateRoundResult(IGameObservable observable, RoundResult result, RoundResult splitResult);

  // opens or closes the given UI frames
  public void updateWindows(IGameObservable observable, boolean playerWindowOpen, boolean playerSplitWindowOpen);
}
