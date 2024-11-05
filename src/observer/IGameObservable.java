package observer;

// Interface for the class that will be observed
public interface IGameObservable {
  // add an observer to be notified
  public void add(IGameObserver observer);

  // remove an observer
  public void remove(IGameObserver observer);

  // update hand on the view
  public void notifyHandUpdate();

  // update bet and balance on the view
  public void notifyMoneyUpdate();

  // display a dialog box with the round result
  public void notifyRoundOver();
}
