package observer;

// Interface for the class that will be observed
public interface IGameObservable {
  // add an observer to be notified
  public void addObserver(IGameObserver observer);

  // remove an observer from the list to be notified
  public void remObserver(IGameObserver observer);

  // send a request to update the hands in the view
  public void notifyHandUpdate();

  // send a request to update the bet and bance values in the view
  public void notifyMoneyUpdate();

  // send a request to display the round result for each hand
  public void notifyRoundOver();
}
