package observer;

import model.GameState;
import model.RoundResult;

// Interface for the class that will be observing
public interface IGameObserver {
  public void updateHand(IGameObservable observable, GameState state);

  public void updateMoney(IGameObservable observable, GameState state);

  public void updateRoundResult(IGameObservable observable, RoundResult result);
}
